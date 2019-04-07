package com.bootdo.redis.shiro;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.PrincipalCollection;

import com.bootdo.domain.SysUserDO;
import com.bootdo.exception.PrincipalIdNullException;
import com.bootdo.exception.PrincipalInstanceException;
import com.bootdo.utils.CollectionUtils;
import com.bootdo.utils.StringUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 参考shiro-redis的开源项目Git地址：https://github.com/alexxiyang/shiro-redis
 * 
 * @author created by zjw on 2019年2月15日 下午2:07:22
 */
@Slf4j
@Getter
@Setter
public class RedisCache<K, V> implements Cache<K, V> {

	private RedisManager redisManager;
	private String keyPrefix = "";
	private int expire = 0;
	private String principalIdFieldName = RedisCacheManager.DEFAULT_PRINCIPAL_ID_FIELD_NAME;

	/**
	 * Construction
	 * 
	 * @param redisManager
	 */
	public RedisCache(RedisManager redisManager, String prefix, int expire, String principalIdFieldName) {

		if (Objects.isNull(redisManager)) {
			throw new IllegalArgumentException("redisManager cannot be null！");
		}
		this.redisManager = redisManager;

		if (StringUtils.isNotBlank(prefix)) {
			this.keyPrefix = prefix;
		}
		if (expire != -1) {
			this.expire = expire;
		}
		if (StringUtils.isNotBlank(principalIdFieldName)) {
			this.principalIdFieldName = principalIdFieldName;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(K key) throws CacheException {
		log.info("get key [{}]", key);

		if (Objects.isNull(key)) {
			return null;
		}

		try {
			String redisCacheKey = this.getRedisCacheKey(key);
			Object rawValue = redisManager.get(redisCacheKey);
			if (Objects.isNull(rawValue)) {
				return null;
			}
			return (V) rawValue;
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	public V put(K key, V value) throws CacheException {
		log.info("put key [{}]", key);

		if (Objects.isNull(key)) {
			log.warn("Saving a null key is meaningless, return value directly without call Redis！");
			return value;
		}
		try {
			redisManager.set(this.getRedisCacheKey(key), Objects.nonNull(value) ? value : null, expire);
			return value;
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public V remove(K key) throws CacheException {
		log.info("remove key [{}]", key);

		if (Objects.isNull(key)) {
			return null;
		}
		try {
			String redisCacheKey = this.getRedisCacheKey(key);
			Object rawValue = redisManager.get(redisCacheKey);
			redisManager.del(redisCacheKey);
			return (V) rawValue;
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	public void clear() throws CacheException {
		log.info("clear cache！");

		Set<String> keys = null;
		try {
			keys = redisManager.scan(this.keyPrefix + "*");
		} catch (Exception e) {
			log.error("get keys error！", e);
		}

		if (CollectionUtils.isEmpty(keys)) {
			return;
		}

		for (String key : keys) {
			redisManager.del(key);
		}
	}

	@Override
	public int size() {
		Long longSize = 0L;
		try {
			longSize = new Long(redisManager.scanSize(this.keyPrefix + "*"));
		} catch (Exception e) {
			log.error("get keys error！", e);
		}
		return longSize.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<K> keys() {

		Set<String> keys = null;
		try {
			keys = redisManager.scan(this.keyPrefix + "*");
		} catch (Exception e) {
			log.error("get keys error！", e);
			return Collections.emptySet();
		}

		if (CollectionUtils.isEmpty(keys)) {
			return Collections.emptySet();
		}

		Set<K> convertedKeys = new HashSet<>();
		for (String key : keys) {
			try {
				convertedKeys.add((K) key);
			} catch (Exception e) {
				log.error("deserialize keys error！", e);
			}
		}
		return convertedKeys;
	}

	@SuppressWarnings("unchecked")
	@Override
    public Collection<V> values() {
		
        Set<String> keys = null;
        try {
            keys = redisManager.scan(this.keyPrefix + "*");
        } catch (Exception e) {
            log.error("get values error！", e);
            return Collections.emptySet();
        }

        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        }

        List<V> values = new ArrayList<>(keys.size());
        for (String key : keys) {
            V value = null;
            try {
                value = (V) redisManager.get(key);
            } catch (Exception e) {
                log.error("deserialize values error！", e);
            }
            if (Objects.nonNull(value)) {
            	values.add(value);
            }
        }
        return Collections.unmodifiableList(values);
    }

	private String getRedisCacheKey(K key) {

		if (Objects.isNull(key)) {
			return null;
		}
		return this.keyPrefix + this.getStringRedisKey(key);
	}

	private String getStringRedisKey(K key) {

		String redisKey;
		if (key instanceof PrincipalCollection) {
			redisKey = this.getRedisKeyFromPrincipalIdField((PrincipalCollection) key);
		} else if (key instanceof SysUserDO) {
			redisKey = ((SysUserDO) key).getUsername();
		} else {
			redisKey = key.toString();
		}
		return redisKey;
	}

	private String getRedisKeyFromPrincipalIdField(PrincipalCollection key) {

		String redisKey;
		Object principalObject = key.getPrimaryPrincipal();
		Method pincipalIdGetter = null;
		Method[] methods = principalObject.getClass().getDeclaredMethods();
		for (Method m : methods) {
			if (Objects.equals(RedisCacheManager.DEFAULT_PRINCIPAL_ID_FIELD_NAME, this.principalIdFieldName)
					&& (Objects.equals("getAuthCacheKey", m.getName()) || Objects.equals("getId", m.getName()))) {
				pincipalIdGetter = m;
				break;
			}
			if (Objects.equals(m.getName(), "get" + this.principalIdFieldName.substring(0, 1).toUpperCase()
					+ this.principalIdFieldName.substring(1))) {
				pincipalIdGetter = m;
				break;
			}
		}
		if (Objects.isNull(pincipalIdGetter)) {
			throw new PrincipalInstanceException(principalObject.getClass(), this.principalIdFieldName);
		}

		try {
			Object idObj = pincipalIdGetter.invoke(principalObject);
			if (Objects.isNull(idObj)) {
				throw new PrincipalIdNullException(principalObject.getClass(), this.principalIdFieldName);
			}
			redisKey = idObj.toString();
		} catch (IllegalAccessException e) {
			throw new PrincipalInstanceException(principalObject.getClass(), this.principalIdFieldName, e);
		} catch (InvocationTargetException e) {
			throw new PrincipalInstanceException(principalObject.getClass(), this.principalIdFieldName, e);
		}
		return redisKey;
	}
}