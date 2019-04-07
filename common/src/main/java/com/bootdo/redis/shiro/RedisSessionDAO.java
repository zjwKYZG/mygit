package com.bootdo.redis.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import com.bootdo.utils.CollectionUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 参考shiro-redis的开源项目Git地址：https://github.com/alexxiyang/shiro-redis
 * 
 * @author created by zjw on 2019年2月15日 下午3:48:21
 */
@Slf4j
@Getter
@Setter
public class RedisSessionDAO extends AbstractSessionDAO {

	private static final String DEFAULT_SESSION_KEY_PREFIX = "shiro:session:";
	private String keyPrefix = DEFAULT_SESSION_KEY_PREFIX;

	private static final long DEFAULT_SESSION_IN_MEMORY_TIMEOUT = 1000L;
	/**
	 * doReadSession be called about 10 times when login. Save Session in
	 * ThreadLocal to resolve this problem. sessionInMemoryTimeout is expiration of
	 * Session in ThreadLocal. The default value is 1000 milliseconds (1s). Most of
	 * time, you don't need to change it.
	 */
	private long sessionInMemoryTimeout = DEFAULT_SESSION_IN_MEMORY_TIMEOUT;

	/**
	 * expire time in seconds
	 */
	private static final int DEFAULT_EXPIRE = -2;
	private static final int NO_EXPIRE = -1;

	/**
	 * Please make sure expire is longer than sesion.getTimeout()
	 */
	private int expire = DEFAULT_EXPIRE;

	private static final int MILLISECONDS_IN_A_SECOND = 1000;

	private RedisManager redisManager;

	@Override
	public void update(Session session) throws UnknownSessionException {

		// 如果会话过期/停止，就没必要再更新了
		try {
			if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
				return;
			}

			if (session instanceof ShiroSession) {
				// 如果没有主要字段（除lastAccessTime字段以外其他字段）发生改变，直接返回
				ShiroSession shiroSession = (ShiroSession) session;
				if (!shiroSession.isChanged()) {
					return;
				}
				// 如果没有直接返回，证明有调用setAttribute往redis放的时候永远设置为false
				shiroSession.setChanged(false);
			}
			this.saveSession(session);
		} catch (Exception e) {
			log.warn("update Session is failed！", e);
		}
	}

	/**
	 * save session
	 * 
	 * @param session
	 * @throws UnknownSessionException
	 */
	private void saveSession(Session session) throws UnknownSessionException {

		if (Objects.isNull(session) || Objects.isNull(session.getId())) {
			log.error("session or session id is null！");
			throw new UnknownSessionException("session or session id is null！");
		}
		String key = getRedisSessionKey(session.getId());
		if (expire == DEFAULT_EXPIRE) {
			this.redisManager.set(key, session, (int) (session.getTimeout() / MILLISECONDS_IN_A_SECOND));
			return;
		}
		if (expire != NO_EXPIRE && expire * MILLISECONDS_IN_A_SECOND < session.getTimeout()) {
			log.warn("Redis session expire time: " + (expire * MILLISECONDS_IN_A_SECOND)
					+ " is less than Session timeout: " + session.getTimeout() + " . It may cause some problems.");
		}
		this.redisManager.set(key, session, expire);
	}

	@Override
	public void delete(Session session) {

		if (Objects.isNull(session) || Objects.isNull(session.getId())) {
			log.error("session or session id is null！");
			return;
		}
		try {
			redisManager.del(getRedisSessionKey(session.getId()));
		} catch (Exception e) {
			log.error("delete session error session id = {}", session.getId());
		}
	}

	@Override
	public Collection<Session> getActiveSessions() {

		Set<Session> sessions = new HashSet<>();
		try {
			Set<String> keys = redisManager.scan(this.keyPrefix + "*");
			if (CollectionUtils.isNotEmpty(keys)) {
				for (String key : keys) {
					Session s = (Session) redisManager.get(key);
					sessions.add(s);
				}
			}
		} catch (Exception e) {
			log.error("get active sessions error！");
		}
		return sessions;
	}

	public Long getActiveSessionsSize() {

		Long size = 0L;
		try {
			size = redisManager.scanSize(this.keyPrefix + "*");
		} catch (Exception e) {
			log.error("get active sessions error！");
		}
		return size;
	}

	@Override
	protected Serializable doCreate(Session session) {

		if (Objects.isNull(session)) {
			log.error("session is null！");
			throw new UnknownSessionException("session is null！");
		}
		Serializable sessionId = super.generateSessionId(session);
		super.assignSessionId(session, sessionId);
		this.saveSession(session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {

		if (Objects.isNull(sessionId)) {
			log.error("session id is null！");
			return null;
		}

		Session session = null;
		try {
			session = (Session) redisManager.get(getRedisSessionKey(sessionId));
		} catch (Exception e) {
			log.error("read session error! sessionId = {}", sessionId);
		}
		return session;
	}

	private String getRedisSessionKey(Serializable sessionId) {
		return this.keyPrefix + sessionId;
	}
}
