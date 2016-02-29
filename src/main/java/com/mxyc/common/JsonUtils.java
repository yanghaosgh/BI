package com.mxyc.common;

import net.sf.json.JSONObject;

public class JsonUtils {

	/**
	 * 根据key获取value
	 * @param json
	 * @param key  
	 * @param defaultValue 
	 * @return
	 */
	public static String getValue(JSONObject json, String key, String defaultValue) {
		Object object = json.get(key);
		return object != null ? object.toString() : defaultValue;
	}

}
