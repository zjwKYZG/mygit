package com.bootdo.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * HttpClient工具类
 * 
 * @author created by zjw on 2019年2月19日 下午2:37:01
 */
public class HttpClientUtils {

	/**
	 * 连接超时
	 */
	public static final int connectionTimeout = 10000;

	/**
	 * 响应超时
	 */
	public static final int responseTimeout = 10000;

	/**
	 * 字符集编码格式
	 */
	private static final String CHARSET_UTF_8 = "UTF-8";

	/**
	 * json数据格式
	 */
	private static final String CONTENT_TYPE_JSON = "application/json";

	/**
	 * xml数据格式
	 */
	private static final String CONTENT_TYPE_XML = "text/xml";

	/**
	 * 发送get请求
	 * 
	 * @param url
	 * @return String
	 */
	public static String get(String url) {

		String result = "";
		if (StringUtils.isBlank(url)) {
			return result;
		}
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(url);
			Builder builder = RequestConfig.custom();
			builder.setConnectTimeout(connectionTimeout);
			builder.setSocketTimeout(responseTimeout);
			httpGet.setConfig(builder.build());
			result = execute(httpClient, httpGet);
		} finally {
			doHttpClientClose(httpClient);
		}
		return result;
	}

	/**
	 * 发送post请求
	 * 
	 * @param url
	 * @param params
	 * @return String
	 */
	public static String post(String url, Map<String, String> params) {
		
		String result = "";
		if (StringUtils.isBlank(url) || MapUtils.isEmpty(params)) {
			return result;
		}
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httpPost = httpPostHandler(url, params);
			Builder builder = RequestConfig.custom();
			builder.setConnectTimeout(connectionTimeout);
			builder.setSocketTimeout(responseTimeout);
			httpPost.setConfig(builder.build());
			result = execute(httpClient, httpPost);
		} finally {
			doHttpClientClose(httpClient);
		}
		return result;
	}

	/**
	 * 发送post请求（json数据格式）
	 * 
	 * @param url
	 * @param jsonStr
	 * @return String
	 */
	public static String postJson(String url, String jsonStr) {

		String result = "";
		if (StringUtils.isBlank(url) || StringUtils.isBlank(jsonStr)) {
			return result;
		}
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url);
			StringEntity stringEntity;
			try {
				stringEntity = new StringEntity(jsonStr);
			} catch (UnsupportedEncodingException e) {
				return result;
			}
			httpPost.setHeader("Content-Type", CONTENT_TYPE_JSON);
			httpPost.setEntity(stringEntity);
			Builder builder = RequestConfig.custom();
			builder.setConnectTimeout(connectionTimeout);
			builder.setSocketTimeout(responseTimeout);
			httpPost.setConfig(builder.build());
			result = execute(httpClient, httpPost);
		} finally {
			doHttpClientClose(httpClient);
		}
		return result;
	}

	/**
	 * 发送post请求（xml数据格式）
	 * 
	 * @param url
	 * @param requestXml
	 * @return String
	 */
	public static String postXml(String url, String requestXml) {

		String result = "";
		if (StringUtils.isBlank(url) || StringUtils.isBlank(requestXml)) {
			return result;
		}
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url);
			StringEntity stringEntity = new StringEntity(requestXml, CHARSET_UTF_8);
			httpPost.addHeader("Content-Type", CONTENT_TYPE_XML);
			httpPost.setEntity(stringEntity);
			Builder builder = RequestConfig.custom();
			builder.setConnectTimeout(connectionTimeout);
			builder.setSocketTimeout(responseTimeout);
			httpPost.setConfig(builder.build());
			result = execute(httpClient, httpPost);
		} finally {
			doHttpClientClose(httpClient);
		}
		return result;
	}

	private static HttpPost httpPostHandler(String url, Map<String, String> params) {
		
		if (StringUtils.isBlank(url) || MapUtils.isEmpty(params)) {
			return null;
		}

		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> list = new ArrayList<>();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(list, CHARSET_UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return httpPost;
	}

	private static String execute(CloseableHttpClient httpClient, HttpUriRequest httpUriRequest) {

		String result = "";
		if (Objects.isNull(httpClient) || Objects.isNull(httpUriRequest)) {
			return result;
		}
		
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpUriRequest);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, CHARSET_UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			doResponseClose(response);
		}
		return result;
	}

	private static void doHttpClientClose(CloseableHttpClient httpClient) {

		if (Objects.nonNull(httpClient)) {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void doResponseClose(CloseableHttpResponse response) {

		if (Objects.nonNull(response)) {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}