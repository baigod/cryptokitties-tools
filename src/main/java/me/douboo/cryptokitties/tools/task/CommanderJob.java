package me.douboo.cryptokitties.tools.task;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import me.douboo.cryptokitties.tools.utils.CattributesUtils;
import me.douboo.cryptokitties.tools.vo.Auction;
import me.douboo.cryptokitties.tools.vo.Cattributes;
import me.douboo.cryptokitties.tools.vo.Kitty;

/**
 * 指令 put take
 * 
 * @author luheng
 *
 */
@Component
@Transactional
public class CommanderJob {

	private final static Logger logger = LoggerFactory.getLogger(SaleCrawlJob.class);
	public static final ConcurrentLinkedQueue<JSONObject> commandQueue1 = new ConcurrentLinkedQueue<JSONObject>();

	public static final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 4);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Scheduled(fixedDelay = 1000)
	public synchronized void exec1() {
		try {
			while (true) {
				JSONObject obj = commandQueue1.poll();
				if (null != obj) {
					pool.execute(new CommanderJobThread(obj));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static final BigDecimal PRICE_DIVISOR = new BigDecimal("1000000000000000000");

	@Transactional
	class CommanderJobThread implements Runnable {

		private JSONObject json;

		public CommanderJobThread(JSONObject json) {
			super();
			this.json = json;
		}

		@Override
		public void run() {
			Auction auction = (Auction) json.get("auction");
			try {
				if (null != auction) {
					long startTime = System.currentTimeMillis();
					int update = jdbcTemplate.update(
							"INSERT INTO `t_auction` (`id`, `start_price`, `end_price`, `current_price`, `duration` , `status`, `type`, `seller_address`, `seller_nickname`, `seller_image` , `kitty_id`) VALUES (?,?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE `start_price` = ?, `end_price` = ?, `current_price` = ?, `duration` = ?, `status` = ?, `type` = ?, `seller_address` = ?, `seller_nickname` = ?, `seller_image` = ?, `kitty_id` = ? ",
							auction.getId(), // id
							auction.getStart_price().divide(PRICE_DIVISOR).setScale(4, RoundingMode.DOWN), // 起始价
							auction.getEnd_price().divide(PRICE_DIVISOR).setScale(4, RoundingMode.DOWN), // 结束价
							auction.getCurrent_price().divide(PRICE_DIVISOR).setScale(4, RoundingMode.DOWN), // 当前价
							auction.getDuration() / 1000 / 60, // 保留到分钟
							auction.getStatus(), // 状态 open
							auction.getType(), // 类型 sale
							auction.getSeller().getAddress(), // 出售者地址
							auction.getSeller().getNickname(), // 出售昵称
							auction.getSeller().getImage(), // 出售者头像
							auction.getKitty().getId(), // 出售者id

							// UPDATE
							auction.getStart_price().divide(PRICE_DIVISOR).setScale(4, RoundingMode.DOWN), // 起始价
							auction.getEnd_price().divide(PRICE_DIVISOR).setScale(4, RoundingMode.DOWN), // 结束价
							auction.getCurrent_price().divide(PRICE_DIVISOR).setScale(4, RoundingMode.DOWN), // 当前价
							auction.getDuration() / 1000 / 60, // 保留到分钟
							auction.getStatus(), // 状态 open
							auction.getType(), // 类型 sale
							auction.getSeller().getAddress(), // 出售者地址
							auction.getSeller().getNickname(), // 出售昵称
							auction.getSeller().getImage(), // 出售者头像
							auction.getKitty().getId() // 出售者id
					);
					if (update == 1) {
						logger.debug("add auction success,id={}", auction.getId());
					} else if (update == 2) {
						logger.debug("update auction success,id={}", auction.getId());
					} else {
						logger.warn("add/update auction fail,id={}", auction.getId());
					}
					logger.trace("auction 执行耗时{}ms", System.currentTimeMillis() - startTime);
				}
			} catch (Exception e) {
				logger.warn("ex={}\n\rauction={}", e.getMessage(), JSONObject.toJSONString(auction));
			}
			Kitty kitty = (Kitty) json.get("kitty");
			try {
				if (null != kitty) {
					long startTime = System.currentTimeMillis();
					// 新增父亲
					if (null != kitty.getSire()) {
						jdbcTemplate.update(
								"INSERT INTO `t_parents` (`id`, `relation`, `name`, `generation`, `owner_wallet_address` , `created_at`, `image_url`, `color`, `is_fancy`, `is_exclusive` , `fancy_type`, `status_is_ready`, `status_is_gestating`, `status_cooldown`, `matron_id` , `sire_id`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE `relation` = ?, `name` = ?, `generation` = ?, `owner_wallet_address` = ?, `created_at` = ?, `image_url` = ?, `color` = ?, `is_fancy` = ?, `is_exclusive` = ?, `fancy_type` = ?, `status_is_ready` = ?, `status_is_gestating` = ?, `status_cooldown` = ?, `matron_id` = ?, `sire_id` = ? ",
								kitty.getSire().getId(), //
								"sire", //
								kitty.getSire().getName(), //
								kitty.getSire().getGeneration(), //
								kitty.getSire().getOwner_wallet_address(), //
								kitty.getSire().getCreated_at(), //
								kitty.getSire().getImage_url(), //
								kitty.getSire().getColor(), //
								kitty.getSire().getIs_fancy(), //
								kitty.getSire().getIs_exclusive(), //
								kitty.getSire().getFancy_type(), //
								kitty.getSire().getStatus().isIs_ready(), //
								kitty.getSire().getStatus().isIs_gestating(), //
								kitty.getSire().getStatus().getCooldown(), //
								null, null, //

								// UPDATE
								"sire", //
								kitty.getSire().getName(), //
								kitty.getSire().getGeneration(), //
								kitty.getSire().getOwner_wallet_address(), //
								kitty.getSire().getCreated_at(), //
								kitty.getSire().getImage_url(), //
								kitty.getSire().getColor(), //
								kitty.getSire().getIs_fancy(), //
								kitty.getSire().getIs_exclusive(), //
								kitty.getSire().getFancy_type(), //
								kitty.getSire().getStatus().isIs_ready(), //
								kitty.getSire().getStatus().isIs_gestating(), //
								kitty.getSire().getStatus().getCooldown(), //
								null, null//
						);
					}
					// 新增母亲
					if (null != kitty.getMatron()) {
						jdbcTemplate.update(
								"INSERT INTO `t_parents` (`id`, `relation`, `name`, `generation`, `owner_wallet_address` , `created_at`, `image_url`, `color`, `is_fancy`, `is_exclusive` , `fancy_type`, `status_is_ready`, `status_is_gestating`, `status_cooldown`, `matron_id` , `sire_id`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE `relation` = ?, `name` = ?, `generation` = ?, `owner_wallet_address` = ?, `created_at` = ?, `image_url` = ?, `color` = ?, `is_fancy` = ?, `is_exclusive` = ?, `fancy_type` = ?, `status_is_ready` = ?, `status_is_gestating` = ?, `status_cooldown` = ?, `matron_id` = ?, `sire_id` = ? ",
								kitty.getMatron().getId(), //
								"matron", //
								kitty.getMatron().getName(), //
								kitty.getMatron().getGeneration(), //
								kitty.getMatron().getOwner_wallet_address(), //
								kitty.getMatron().getCreated_at(), //
								kitty.getMatron().getImage_url(), //
								kitty.getMatron().getColor(), //
								kitty.getMatron().getIs_fancy(), //
								kitty.getMatron().getIs_exclusive(), //
								kitty.getMatron().getFancy_type(), //
								kitty.getMatron().getStatus().isIs_ready(), //
								kitty.getMatron().getStatus().isIs_gestating(), //
								kitty.getMatron().getStatus().getCooldown(), //
								null, null, //

								// UPDATE
								"sire", //
								kitty.getMatron().getName(), //
								kitty.getMatron().getGeneration(), //
								kitty.getMatron().getOwner_wallet_address(), //
								kitty.getMatron().getCreated_at(), //
								kitty.getMatron().getImage_url(), //
								kitty.getMatron().getColor(), //
								kitty.getMatron().getIs_fancy(), //
								kitty.getMatron().getIs_exclusive(), //
								kitty.getMatron().getFancy_type(), //
								kitty.getMatron().getStatus().isIs_ready(), //
								kitty.getMatron().getStatus().isIs_gestating(), //
								kitty.getMatron().getStatus().getCooldown(), //
								null, null//
						);
					}
					String catts = cattributes(kitty.getCattributes());
					int sum = CattributesUtils.calcSum(catts);
					double avg = CattributesUtils.calcAvg(catts);
					JSONArray tagArray = CattributesUtils.makeTag(catts);
					int update = jdbcTemplate.update(
							"INSERT INTO `t_kitty` (`id`, `kitty_name`, `image_url`, `generation`, `created_at` , `color`, `is_fancy`, `is_exclusive`, `fancy_type`, `owner_address` , `owner_nickname`, `owner_image`, `status_is_ready`, `status_is_gestating`, `status_cooldown` , `status_cooldown_index`, `bio`, `matron_id`, `sire_id`, `children_num` , `cattributes`, `cattributes_sum`, `cattributes_avg`, `cattributes_tags`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE `kitty_name` = ?, `image_url` = ?, `generation` = ?, `created_at` = ?, `color` = ?, `is_fancy` = ?, `is_exclusive` = ?, `fancy_type` = ?, `owner_address` = ?, `owner_nickname` = ?, `owner_image` = ?, `status_is_ready` = ?, `status_is_gestating` = ?, `status_cooldown` = ?, `status_cooldown_index` = ?, `bio` = ?, `matron_id` = ?, `sire_id` = ?, `children_num` = ?, `cattributes` = ?, `cattributes_sum` = ?, `cattributes_avg` = ?, `cattributes_tags` = ?",
							kitty.getId(), // id
							kitty.getName(), // name
							kitty.getImage_url(), // url
							kitty.getGeneration(), // gen
							kitty.getCreated_at(), // date
							kitty.getColor(), // color
							kitty.getIs_fancy(), // fancy
							kitty.getIs_exclusive(), // 独家
							kitty.getFancy_type(), // fancy
							kitty.getOwner().getAddress(), // addr
							kitty.getOwner().getNickname(), // nickname
							kitty.getOwner().getImage(), // img
							kitty.getStatus().isIs_ready(), // ready
							kitty.getStatus().isIs_gestating(), // 怀孕
							kitty.getStatus().getCooldown(), // 冷却时间
							kitty.getStatus().getCooldown_index(), // 冷却等级
							kitty.getBio(), // 简历
							kitty.getMatron().getId(), // matron
							kitty.getSire().getId(), // sire
							kitty.getChildren().size(), // children_num
							catts, // Cattributes
							sum, // 属性总和
							avg, // 属性均分
							tagArray.toJSONString(), // 属性标签

							// UPDATE

							kitty.getName(), // name
							kitty.getImage_url(), // url
							kitty.getGeneration(), // gen
							kitty.getCreated_at(), // date
							kitty.getColor(), // color
							kitty.getIs_fancy(), // fancy
							kitty.getIs_exclusive(), // 独家
							kitty.getFancy_type(), // fancy
							kitty.getOwner().getAddress(), // addr
							kitty.getOwner().getNickname(), // nickname
							kitty.getOwner().getImage(), // img
							kitty.getStatus().isIs_ready(), // ready
							kitty.getStatus().isIs_gestating(), // 怀孕
							kitty.getStatus().getCooldown(), // 冷却时间
							kitty.getStatus().getCooldown_index(), // 冷却等级
							kitty.getBio(), // 简历
							kitty.getMatron().getId(), // matron
							kitty.getSire().getId(), // sire
							kitty.getChildren().size(), // children_num
							catts, // Cattributes
							sum, // 属性总和
							avg, // 属性均分
							tagArray.toJSONString() // 属性标签
					);

					if (update == 1) {
						logger.debug("add kitty success,id={}", auction.getId());
					} else if (update == 2) {
						logger.debug("update kitty success,id={}", auction.getId());
					} else {
						logger.warn("add/update kitty fail,id={}", auction.getId());
					}
					logger.trace("kitty 执行耗时{}ms", System.currentTimeMillis() - startTime);
				}
			} catch (Exception e) {
				logger.warn(ExceptionUtils.getStackTrace(e));
			}

		}

		private String cattributes(List<Cattributes> cattributes) {
			// 属性排序
			cattributes.sort(new Comparator<Cattributes>() {
				@Override
				public int compare(Cattributes o1, Cattributes o2) {
					List<Entry<String, Integer>> sortAttrs = CattributesUtils.sort(true);
					int o1Index = 0, o2Index = 0;
					for (int i = 0; i < sortAttrs.size(); i++) {
						Entry<String, Integer> attr = sortAttrs.get(i);
						if (attr.getKey().equals(o1.getDescription().trim())) {
							o1Index = i;
						} else if (attr.getKey().equals(o2.getDescription().trim())) {
							o2Index = i;
						}
					}
					return o1Index - o2Index;
				}
			});
			// 瓶装
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < cattributes.size(); i++) {
				Cattributes c = cattributes.get(i);
				sb.append(c.getDescription().trim());
				if (i < cattributes.size() - 1) {
					sb.append(",");
				}
			}
			return sb.toString();
		}
	}
}
