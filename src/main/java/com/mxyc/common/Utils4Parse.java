package com.mxyc.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Utils4Parse {
	// private static final Logger log = Logger.getLogger(Utils4Parse.class);

	/**
	 * 解析json格式字符串<br>
	 * ts转化为东八区时间后: >server_time1天 | <server_time6个月,视为无效时间，以server_time为准
	 * 
	 * @param params
	 *            带解析字符串
	 * @param checkDate
	 *            是否进行日期范围过滤
	 * @return
	 */
	public static List<Map<String, String>> parseLog4default(String params) {
		if (StringUtils.isEmpty(params)) {
			return null;
		}

		JSONObject jsonObject = JSONObject.fromObject(params);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		JSONArray array = jsonObject.getJSONArray("events");
		Iterator<?> it = array.iterator();
		JSONObject obj = null;

		Map<String, String> globalMap = new HashMap<String, String>();
		Map<String, String> localMap = null;

		setValues(globalMap, jsonObject, DataUtils.attrs_g);
		Date sDate = CommonUtils.createDate(CommonUtils.date_format[2], JsonUtils.getValue(jsonObject, "server_time", ""));
		Date halfYearAgo = CommonUtils.createDate(sDate, 0, -6, 0, 0);
		Date oneDayLater = CommonUtils.createDate(sDate, 0, 0, 1, 0);
		Date ts = null;

		while (it.hasNext()) {
			obj = (JSONObject) it.next();
			localMap = new HashMap<String, String>();

			setValues(localMap, jsonObject, DataUtils.attrs_g);
			setValues(localMap, obj, DataUtils.attrs_l);

			// 根据ts 获取cdate,sdate默认取cdate
			// ts:utc格式时间
			ts = CommonUtils.createDate(CommonUtils.date_format[2], localMap.get("ts"));
			ts = CommonUtils.createDate(ts, 0, 0, 0, 8);
			if (ts.before(halfYearAgo) || ts.after(oneDayLater)) {
				localMap.put("cdate", CommonUtils.createDate(CommonUtils.date_format[1], sDate, 0, 0, 0));
			} else {
				localMap.put("cdate", CommonUtils.createDate(CommonUtils.date_format[1], ts, 0, 0, 0));
			}

			list.add(localMap);
		}

		return list;
	}

	/**
	 * 批量根据key获取value
	 * 
	 * @param map
	 * @param json
	 * @param keys
	 */
	public static void setValues(Map<String, String> map, JSONObject json, String[] keys) {
		if (json == null || keys == null) {
			return;
		}

		for (String key : keys) {
			map.put(key, JsonUtils.getValue(json, key, ""));
		}
	}

}
