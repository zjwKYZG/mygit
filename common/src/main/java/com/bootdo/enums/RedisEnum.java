package com.bootdo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * redis key枚举类.
 * 
 * @author created by zjw on 2019年1月22日 下午6:05:24
 */
@AllArgsConstructor
@Getter
public enum RedisEnum {

	/**
	 * 会话的Redis key前缀.
	 */
	SHIRO_REDIS_SESSION("shiro_redis_session："),

	/**
	 * 缓存的Redis key前缀.
	 */
	SHIRO_REDIS_CACHE("shiro_redis_cache："),

	/**
	 * 用户队列的Redis key前缀，用于实现并发登录控制.
	 */
	SHIRO_REDIS_USER_DEQUE("shiro_redis_user_deque：");

	private String key;
}