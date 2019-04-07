package com.bootdo.exception;

/**
 * 读取用户权限信息用到的异常类
 * 
 * @author created by zjw on 2019年2月15日 下午3:46:09
 */
@SuppressWarnings("serial")
public class PrincipalIdNullException extends RuntimeException {

	private static final String MESSAGE = "Principal Id shouldn't be null!";

	public PrincipalIdNullException(@SuppressWarnings("rawtypes") Class clazz, String idMethodName) {
		super(clazz + " id field: " + idMethodName + ", value is null\n" + MESSAGE);
	}
}
