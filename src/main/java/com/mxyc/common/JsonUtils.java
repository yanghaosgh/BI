package com.mxyc.common;

import net.sf.json.JSONObject;

public class JsonUtils {

	/**
	 * 根据key获取value
	 * 
	 * @param json
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getValue(JSONObject json, String key, String defaultValue) {
		if (json == null || json.isNullObject()) {
			return defaultValue;
		}

		Object object = json.get(key);
		return object != null ? object.toString() : defaultValue;
	}

}
