package me.douboo.cryptokitties.tools.task;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import me.douboo.cryptokitties.tools.data.Kitties;
import me.douboo.cryptokitties.tools.utils.HttpClientFactory;
import me.douboo.cryptokitties.tools.vo.Auction;
import me.douboo.cryptokitties.tools.vo.Kitty;

@Component
public class SaleCrawlJob {

	private final static Logger logger = LoggerFactory.getLogger(SaleCrawlJob.class);

	public static final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 4);

	@Scheduled(fixedDelay = 1000, initialDelay = 5000)
	public synchronized void exec1() {
		try {
			// 查询第一条
			String ret = curl("https://api.cryptokitties.co/auctions?offset=0&limit=1&type=sale&status=open");//&parents=false
			logger.debug("查询第一条：{}", ret);
			if (StringUtils.isNotEmpty(ret)) {
				JSONObject json = JSONObject.parseObject(ret);
				if (null != json) {
					int total = json.getIntValue("total");
					int group = 20;
					int p = (int) Math.ceil(total / (double) group);
					logger.debug("共{}只，分成{}批抓取，每批{}只", total, p, group);
					Kitties.sales.clear();
					CountDownLatch latch = new CountDownLatch(p);
					for (int i = 0; i < p; i++) {
						pool.execute(new SaleCrawlJobThread(i * group, group, latch));
					}
					latch.await();
				}
			}

			if (!CollectionUtils.isEmpty(Kitties.sales)) {
				// 清理销售库
				pool.execute(new AuctionCleanlJobThread());
				// 遍历所有猫详情
				CountDownLatch latch = new CountDownLatch(Kitties.sales.entrySet().size());
				for (Entry<Integer, Auction> auction : Kitties.sales.entrySet()) {
					pool.execute(new SaleKittyCrawlJobThread(auction.getValue(), latch));
				}
				latch.await();
			}

			while (CommanderJob.commandQueue1.size() != 0) {
			}
			logger.info("结束抓取周期...");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	class AuctionCleanlJobThread implements Runnable {

		@Override
		public void run() {
			Set<Integer> ids = Kitties.sales.keySet();
			int update = jdbcTemplate.update("DELETE FROM t_auction WHERE id NOT IN (" + StringUtils.join(ids.toArray(), ",") + ")");
			logger.info("清理{}条auction", update);
			update = jdbcTemplate.update("DELETE FROM t_kitty WHERE id NOT IN (SELECT kitty_id FROM t_auction)");
			logger.info("清理{}条kitty", update);
		}
	}

	class SaleKittyCrawlJobThread implements Runnable {
		private Auction auction;
		private CountDownLatch latch;

		public SaleKittyCrawlJobThread(Auction auction, CountDownLatch latch) {
			super();
			this.auction = auction;
			this.latch = latch;
		}

		@Override
		public void run() {
			int id = -1;
			try {
				id = auction.getKitty().getId();
				String ret = curl("https://api.cryptokitties.co/kitties/" + id);
				logger.trace("获得猫详情{}", ret);
				if (StringUtils.isNotEmpty(ret)) {
					Kitty kitty = JSONObject.parseObject(ret, Kitty.class);
					if (null != kitty) {
						JSONObject cmdObj = new JSONObject();
						cmdObj.put("auction", auction);
						cmdObj.put("kitty", kitty);
						CommanderJob.commandQueue1.offer(cmdObj);
					}
				}
			} catch (Exception e) {
				logger.warn("id:{},e:{}", id, e.getMessage());
			} finally {
				latch.countDown();
			}
		}

	}

	class SaleCrawlJobThread implements Runnable {
		private int offset;
		private int limit;
		private CountDownLatch latch;

		public SaleCrawlJobThread(int offset, int limit, CountDownLatch latch) {
			super();
			this.offset = offset;
			this.limit = limit;
			this.latch = latch;
		}

		@Override
		public void run() {
			try {
				String ret = curl("https://api.cryptokitties.co/auctions?offset=" + offset + "&limit=" + limit + "&type=sale&status=open&sorting=cheap&orderBy=current_price&orderDirection=asc");// &parents=false
				if (StringUtils.isNotEmpty(ret)) {
					JSONObject json = JSONObject.parseObject(ret);
					if (null != json) {
						JSONArray jsonArray = json.getJSONArray("auctions");
						if (jsonArray != null) {
							logger.debug("当前查询第{}条--{}条,得{}条", offset, offset + limit, jsonArray.size());
							for (int i = 0; i < jsonArray.size(); i++) {
								Auction auction = JSONObject.parseObject(jsonArray.getJSONObject(i).toJSONString(), Auction.class);
								logger.trace("auction[{}]:{}", offset + i, JSONObject.toJSONString(auction));
								Kitties.sales.put(auction.getId(), auction);
							}
						}
					}
				}
			} catch (Exception e) {
				logger.warn("第{}批查询失败:{}", offset / limit, ExceptionUtils.getStackTrace(e));
			} finally {
				latch.countDown();
			}
		}
	}

	private String curl(String uri) {
		try {
			long startTime = System.currentTimeMillis();
			HttpClientFactory fac = HttpClientFactory.createSSLInstance();
			int timeout = 10000;
			fac.setSocketTimeout(timeout);
			fac.setRequestTimeout(timeout);
			fac.setConnectTimeout(timeout);
			fac.setRetry(3);
			fac.addPostHeader("Origin", "https://www.cryptokitties.co");
			fac.addPostHeader("Referer", "https://www.cryptokitties.co/marketplace/siring/1?orderBy=current_price&orderDirection=asc&sorting=cheap");
			fac.addPostHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.89 Safari/537.36 " + System.currentTimeMillis());
			String randomIp = RandomUtils.nextInt(0, 255) + "." + RandomUtils.nextInt(0, 255) + "." + RandomUtils.nextInt(0, 255) + "." + RandomUtils.nextInt(0, 255);
			fac.addPostHeader("x-forwarded-for", randomIp);
			fac.addPostHeader("Proxy-Client-IP", randomIp);
			fac.addPostHeader("WL-Proxy-Client-IP", randomIp);
			String ret = fac.doGet(uri);
			logger.trace("uri:{}耗时{}ms", uri, System.currentTimeMillis() - startTime);
			return ret;
		} catch (Exception e) {
			logger.warn("uri:{},e:{}", uri, e.getMessage());
		}
		return null;
	}

}
