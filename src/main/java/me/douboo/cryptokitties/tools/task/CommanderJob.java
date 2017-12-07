package me.douboo.cryptokitties.tools.task;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import com.alibaba.fastjson.JSONObject;

/**
 * 指令 put take
 * 
 * @author luheng
 *
 */
public class CommanderJob {

	public static final LinkedBlockingQueue<Map<Integer, JSONObject>> commandQueue1 = new LinkedBlockingQueue<>(3);
}
