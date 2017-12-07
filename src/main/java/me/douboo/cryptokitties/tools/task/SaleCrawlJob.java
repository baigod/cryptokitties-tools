package me.douboo.cryptokitties.tools.task;

import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import me.douboo.cryptokitties.tools.data.Kitties;
import me.douboo.cryptokitties.tools.utils.HttpClientFactory;
import me.douboo.cryptokitties.tools.vo.Auction;

@Component
public class SaleCrawlJob {

	private final static Logger logger = LoggerFactory.getLogger(SaleCrawlJob.class);
	private final static HttpClientFactory fac = HttpClientFactory.createSSLInstance();
	static {
		fac.addPostHeader("Origin", "https://www.cryptokitties.co");
		fac.addPostHeader("Referer", "https://www.cryptokitties.co/marketplace/siring/2?orderBy=current_price&orderDirection=asc&sorting=cheap");
		fac.addPostHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.89 Safari/537.36");
	}

	private static int cpu = Runtime.getRuntime().availableProcessors() / 2;
	public static final ExecutorService pool = Executors.newFixedThreadPool(cpu);

	@Scheduled(fixedDelay = 10000)
	public synchronized void exec1() {
		try {
			// 查询第一条
			String ret = fac.doGet("https://api.cryptokitties.co/auctions?offset=0&limit=1&type=sire&status=open&sorting=cheap&orderBy=current_price&orderDirection=asc");
			logger.debug("查询第一条：{}", ret);
			if (StringUtils.isNotEmpty(ret)) {
				JSONObject json = JSONObject.parseObject(ret);
				if (null != json) {
					int total = json.getIntValue("total");
					int group = total / cpu;
					logger.debug("共{}只，分成{}批抓取，每批{}只", total, ++cpu, group);
					Kitties.sales.clear();
					CountDownLatch latch = new CountDownLatch(cpu);
					for (int i = 0; i < cpu; i++) {
						pool.execute(new SaleCrawlJobThread(i * group, group, latch));
						Thread.sleep(300);
					}
					latch.await();
				}
			}
			// 遍历所有猫详情
			if (!CollectionUtils.isEmpty(Kitties.sales)) {
				CountDownLatch latch = new CountDownLatch(Kitties.sales.entrySet().size());
				for (Entry<Integer, Auction> auction : Kitties.sales.entrySet()) {
					pool.execute(new SaleKittyCrawlJobThread(auction.getValue(), latch));
					Thread.sleep(300);
				}
				latch.await();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	static class SaleKittyCrawlJobThread implements Runnable {
		private Auction auction;
		private CountDownLatch latch;

		public SaleKittyCrawlJobThread(Auction auction, CountDownLatch latch) {
			super();
			this.auction = auction;
			this.latch = latch;
		}

		@Override
		public void run() {
			try {
				int id = auction.getId();
				String ret = fac.doGet("https://api.cryptokitties.co/kitties/"+id);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				latch.countDown();
			}
		}

	}

	static class SaleCrawlJobThread implements Runnable {
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
				String ret = fac.doGet("https://api.cryptokitties.co/auctions?offset=" + offset + "&limit=" + limit + "&type=sire");// &status=open&sorting=cheap&orderBy=current_price&orderDirection=asc
				logger.debug("当前查询第{}条--{}条,得{}", offset, offset + limit, ret);
				if (StringUtils.isNotEmpty(ret)) {
					JSONObject json = JSONObject.parseObject(ret);
					if (null != json) {
						JSONArray jsonArray = json.getJSONArray("auctions");
						if (jsonArray != null) {
							for (int i = 0; i < jsonArray.size(); i++) {
								Auction auction = JSONObject.parseObject(jsonArray.getJSONObject(i).toJSONString(), Auction.class);
								logger.trace("auction[{}]:{}", offset + i, JSONObject.toJSONString(auction));
								Kitties.sales.put(auction.getId(), auction);
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				latch.countDown();
			}

		}

	}
}
