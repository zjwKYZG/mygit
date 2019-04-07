package com.bootdo.config;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import lombok.extern.slf4j.Slf4j;

/**
 * 配置session监听器
 * 
 * @author created by zjw on 2019年1月20日 下午4:10:48
 */
@Slf4j
public class BDSessionListener implements SessionListener {

	/**
	 * 统计在线人数（线程安全自增）
	 */
	private final AtomicInteger sessionCount = new AtomicInteger(0);

	/**
	 * 会话创建时触发
	 */
	@Override
	public void onStart(Session session) {
		log.info("会话创建： {}", session.getId());
		// 会话创建，在线人数+1
		sessionCount.incrementAndGet();
	}

	/**
	 * 会话退出时触发
	 */
	@Override
	public void onStop(Session session) {
		log.info("会话退出： {}", session.getId());
		// 会话退出，在线人数-1
		sessionCount.decrementAndGet();
	}

	/**
	 * 会话过期时触发
	 */
	@Override
	public void onExpiration(Session session) {
		log.info("会话过期： {}", session.getId());
		// 会话过期，在线人数-1
		sessionCount.decrementAndGet();
	}

	/**
	 * 获取在线人数
	 * 
	 * @return int
	 */
	public int getSessionCount() {
		return sessionCount.get();
	}
}