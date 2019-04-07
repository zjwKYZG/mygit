package com.bootdo.redis.shiro;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.apache.shiro.session.mgt.SimpleSession;

import lombok.Getter;
import lombok.Setter;

/**
 * 由于SimpleSession的lastAccessTime更改后也会调用SessionDAO的update方法，
 * 增加标识位，如果只是更改lastAccessTime字段，SessionDao的update方法直接返回
 * 
 * @author created by zjw on 2019年2月15日 下午4:40:36
 */
@Getter
@Setter
public class ShiroSession extends SimpleSession implements Serializable {

	private static final long serialVersionUID = -3336162540576125919L;

	// 除lastAccessTime字段以外其他字段发生改变时为true
	private boolean isChanged = false;

	public ShiroSession() {
		super();
		this.setChanged(true);
	}

	public ShiroSession(String host) {
		super(host);
		this.setChanged(true);
	}

	@Override
	public void setId(Serializable id) {
		super.setId(id);
		this.setChanged(true);
	}

	@Override
	public void setStopTimestamp(Date stopTimestamp) {
		super.setStopTimestamp(stopTimestamp);
		this.setChanged(true);
	}

	@Override
	public void setExpired(boolean expired) {
		super.setExpired(expired);
		this.setChanged(true);
	}

	@Override
	public void setTimeout(long timeout) {
		super.setTimeout(timeout);
		this.setChanged(true);
	}

	@Override
	public void setHost(String host) {
		super.setHost(host);
		this.setChanged(true);
	}

	@Override
	public void setAttributes(Map<Object, Object> attributes) {
		super.setAttributes(attributes);
		this.setChanged(true);
	}

	@Override
	public void setAttribute(Object key, Object value) {
		super.setAttribute(key, value);
		this.setChanged(true);
	}

	@Override
	public Object removeAttribute(Object key) {
		this.setChanged(true);
		return super.removeAttribute(key);
	}

	/**
	 * 停止
	 */
	@Override
	public void stop() {
		super.stop();
		this.setChanged(true);
	}

	/**
	 * 设置过期
	 */
	@Override
	protected void expire() {
		this.stop();
		this.setExpired(true);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	protected boolean onEquals(SimpleSession ss) {
		return super.onEquals(ss);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return super.toString();
	}
}