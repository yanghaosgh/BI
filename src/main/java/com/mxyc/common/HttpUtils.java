package com.mxyc.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	public static final String encoding = "UTF-8";

	/**
	 * 
	 * @param url
	 * @param params
	 */
	public static String post(String url, Map<String, String> params) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if (params != null && !params.isEmpty()) {
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				list.add(new BasicNameValuePair(key, params.get(key)));
			}
		}

		UrlEncodedFormEntity uefEntity = null;
		CloseableHttpResponse response = null;
		String result = null;
		try {
			uefEntity = new UrlEncodedFormEntity(list, encoding);
			httppost.setEntity(uefEntity);
			response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, encoding);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
				}
			}
			try {
				httpclient.close();
			} catch (IOException e) {
			}
		}

		return result;
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	public static String get(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String result = null;
		try {
			response = httpclient.execute(new HttpGet(url));
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
				}
			}
			try {
				httpclient.close();
			} catch (IOException e) {
			}
		}

		return result;
	}

}