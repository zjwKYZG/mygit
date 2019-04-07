package com.bootdo.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Objects;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.minidev.json.JSONObject;

/**
 * HttpServlet工具类
 * 
 * @author created by zjw on 2019年1月21日 下午2:51:40
 */
public class HttpServletUtils {

	public static boolean isAjaxRequest(HttpServletRequest request) {
		// 判断是否为ajax请求，默认不是
		boolean isAjaxRequest = false;
		if (StringUtils.isNotBlank(request.getHeader("x-requested-with"))
				&& Objects.equals("XMLHttpRequest", request.getHeader("x-requested-with"))) {
			isAjaxRequest = true;
		}
		return isAjaxRequest;
	}

	public static void out(ServletResponse response, Map<String, String> resultMap) throws IOException {
		try {
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.println(JSONObject.toJSONString(resultMap));
			out.flush();
			out.close();
		} catch (Exception e) {

		}
	}
}