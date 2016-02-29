package com.mxyc.common;

import java.util.Map;

public class DataUtils {
	public static final String[] attrs_g = { "gv", "gf", "gc", "access_token", "uid", "push_token", "gi", "os", "gos", "gs", "net", "isp", "ts", "duration", "gt", "appkey", "member_level" };
	public static final String[] attrs_l = { "b_post", "banner_id", "brand_id", "business_id", "cate_id", "designer_id", "detail_id", "duration", "eventname", //
			"expert_id", "from_chat", "is_presell", "is_push", "keyword", "keywords", "on_sale", "order_id", "part_id", "position", //
			"price", "showId", "showType", "showTypeid", "side", "sku_id", "source", "source_id", "star_id", "tab_1", "tab_2", "tab_3", "tab_4", "thread_id", //
			"topic_id", "ts", "tuan_id", "prefecture_id", "type", "type_id", "waittime", "ad_post", "favor_type", "user_type", "show_detail_id", "utm_source", "cdate" };

	public static final String[] attrs_all = { "gv", "gf", "gc", "access_token", "uid", "push_token", "gi", "os", "gos", "gs", "net", "isp", "ts", "duration", "gt", "appkey", "member_level", //
			"b_post", "banner_id", "brand_id", "business_id", "cate_id", "designer_id", "detail_id", "eventname", "expert_id", //
			"from_chat", "is_presell", "is_push", "keyword", "keywords", "on_sale", "order_id", "part_id", "position", //
			"price", "showId", "showType", "showTypeid", "side", "sku_id", "source", "source_id", "star_id", "tab_1", "tab_2", "tab_3", //
			"tab_4", "thread_id", "topic_id", "tuan_id", "prefecture_id", "type", "type_id", "waittime", "ad_post", "favor_type", "user_type", "show_detail_id", "utm_source", "cdate" };

	public static final String[] attrs_paid_all = { "user_id", "source_id", "goods_id", "order_id", "pay_order_id", "pay_count", "pay_price", //
			"pay_amount", "ts", "title", "url_img", "banner_id", "topic_id", "tuan_id", "prefecture_id", "brand_id", "business_id", "cate_id", "designer_id", "detail_id", "source", //
			"tab_1", "tab_2", "tab_3", "tab_4", "bannder_duration", "utm_source" ,"position"};

	public static String toLoString(Map<String, String> map, String[] keys) {
		StringBuilder sb = new StringBuilder();
		int index = keys.length - 1;

		for (int i = 0; i < keys.length; i++) {
			if (i < index) {
				sb.append(map.get(keys[i]) + "\001");
			} else {
				sb.append(map.get(keys[i]));
			}
		}

		return sb.toString();
	}

}
