package com.bootdo.redis.shiro;

import java.io.Serializable;
import java.util.Objects;

import javax.servlet.ServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

/**
 * 解决单次请求需要多次访问redis，另一种方案（从request中获取）
 * 
 * @author created by zjw on 2019年2月15日 下午4:23:55
 */
public class ShiroSessionManager extends DefaultWebSessionManager {

	/**
	 * 获取session，优化单次请求需要多次访问redis的问题
	 * 
	 * @param sessionKey
	 * @return Session
	 * @throws UnknownSessionException
	 */
	@Override
	protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {

		Serializable sessionId = super.getSessionId(sessionKey);

		ServletRequest request = null;
		if (sessionKey instanceof WebSessionKey) {
			request = ((WebSessionKey) sessionKey).getServletRequest();
		}

		if (Objects.nonNull(request) && Objects.nonNull(sessionId)) {
			Object sessionObj = request.getAttribute(sessionId.toString());
			if (Objects.nonNull(sessionObj)) {
				return (Session) sessionObj;
			}
		}

		Session session = super.retrieveSession(sessionKey);
		if (Objects.nonNull(request) && Objects.nonNull(sessionId)) {
			request.setAttribute(sessionId.toString(), session);
		}
		return session;
	}
}