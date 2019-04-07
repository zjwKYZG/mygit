package com.bootdo.redis.shiro;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 参考shiro-redis的开源项目Git地址：https://github.com/alexxiyang/shiro-redis
 * 
 * @author created by zjw on 2019年2月15日 下午2:11:22
 */
@Slf4j
@Getter
@Setter
public class RedisCacheManager implements CacheManager {

	/**
	 * fast lookup by name map
	 */
	@SuppressWarnings("rawtypes")
	private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<>();

	private RedisManager redisManager;

	/**
	 * expire time in seconds
	 */
	private static final int DEFAULT_EXPIRE = 1800;
	private int expire = DEFAULT_EXPIRE;

	/**
	 * The Redis key prefix for caches
	 */
	public static final String DEFAULT_CACHE_KEY_PREFIX = "shiro:cache:";
	private String keyPrefix = DEFAULT_CACHE_KEY_PREFIX;

	public static final String DEFAULT_PRINCIPAL_ID_FIELD_NAME = "authCacheKey or id";
	private String principalIdFieldName = DEFAULT_PRINCIPAL_ID_FIELD_NAME;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		
		log.info("*************get cache, name = {} **************", name);

		Cache cache = caches.get(name);

		if (Objects.isNull(cache)) {
			cache = new RedisCache<>(redisManager, keyPrefix + name + ":", expire, principalIdFieldName);
			caches.put(name, cache);
		}
		return cache;
	}
}