package me.douboo.cryptokitties.tools.utils;

import org.apache.commons.lang3.ArrayUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class CooldownUtils {

	public static final JSONArray cooldownIndexes = JSONObject.parseArray(
			"[{\"name\":\"Fast\",\"indexes\":[0],\"readable\":\"1m\"},{\"name\":\"Swift\",\"indexes\":[1,2],\"readable\":\"2m - 5m\"},{\"name\":\"Snappy\",\"indexes\":[3,4],\"readable\":\"10m - 30m\"},{\"name\":\"Brisk\",\"indexes\":[5,6],\"readable\":\"1h - 2h\"},{\"name\":\"Plodding\",\"indexes\":[7,8],\"readable\":\"4h - 8h\"},{\"name\":\"Slow\",\"indexes\":[9,10],\"readable\":\"16h - 24h\"},{\"name\":\"Sluggish\",\"indexes\":[11,12],\"readable\":\"2d - 4d\"},{\"name\":\"Catatonic\",\"indexes\":[13],\"readable\":\"1 week\"}]");

	public static String nameByIndex(int index) {
		for (int i = 0; i < cooldownIndexes.size(); i++) {
			JSONObject object = cooldownIndexes.getJSONObject(i);
			if (ArrayUtils.contains(object.getJSONArray("indexes").toArray(), index)) {
				return object.getString("name");
			}
		}
		return "UnKnow";
	}

	public static String readableByIndex(int index) {
		for (int i = 0; i < cooldownIndexes.size(); i++) {
			JSONObject object = cooldownIndexes.getJSONObject(i);
			if (ArrayUtils.contains(object.getJSONArray("indexes").toArray(), index)) {
				return object.getString("readable");
			}
		}
		return "UnKnow";
	}
}
