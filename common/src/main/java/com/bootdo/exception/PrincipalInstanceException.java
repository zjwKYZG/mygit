package com.bootdo.exception;

/**
 * 读取用户权限信息用到的异常类
 * 
 * @author created by zjw on 2019年2月15日 下午3:43:07
 */
@SuppressWarnings("serial")
public class PrincipalInstanceException extends RuntimeException {

	private static final String MESSAGE = "We need a field to identify this Cache Object in Redis. "
			+ "So you need to defined an id field which you can get unique id to identify this principal. "
			+ "For example, if you use UserInfo as Principal class, the id field maybe userId, userName, email, etc. "
			+ "For example, getUserId(), getUserName(), getEmail(), etc.\n"
			+ "Default value is authCacheKey or id, that means your principal object has a method called \"getAuthCacheKey()\" or \"getId()\"";

	@SuppressWarnings("rawtypes")
	public PrincipalInstanceException(Class clazz, String idMethodName) {
		super(clazz + " must has getter for field: " + idMethodName + "\n" + MESSAGE);
	}

	@SuppressWarnings("rawtypes")
	public PrincipalInstanceException(Class clazz, String idMethodName, Exception e) {
		super(clazz + " must has getter for field: " + idMethodName + "\n" + MESSAGE, e);
	}
}
