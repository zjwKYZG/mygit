package com.bootdo.redis.shiro;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.DefaultWebSessionContext;

import com.bootdo.utils.StringUtils;

/**
 * 
 * @author created by zjw on 2019年2月15日 下午5:01:18
 */
public class ShiroSessionFactory implements SessionFactory {

	@Override
	public Session createSession(SessionContext initData) {

		ShiroSession session = new ShiroSession();
		HttpServletRequest request = (HttpServletRequest) initData
				.get(DefaultWebSessionContext.class.getName() + ".SERVLET_REQUEST");
		session.setHost(getIpAddress(request));
		return session;
	}

	public static String getIpAddress(HttpServletRequest request) {

		String ip = request.getHeader("x-forwarded-for");
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.contains(",")) {
			return ip.split(",")[0];
		} else {
			return ip;
		}
	}
}