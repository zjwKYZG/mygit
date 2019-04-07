package com.bootdo.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * MD5加密工具类
 * 
 * @author created by zjw on 2019年1月21日 下午2:12:34
 */
public class MD5Utils {

	/**
	 * shiro密码盐
	 */
	private static final String SALT = "1qazxsw2";

	/**
	 * 加密算法名称
	 */
	private static final String ALGORITH_NAME = "md5";

	/**
	 * 哈希的迭代
	 */
	private static final int HASH_ITERATIONS = 2;

	private final static char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	/**
	 * 根据用户名和密码以及shiro密码盐进行md5加密处理
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @return String 加密后的密码
	 */
	public static String encrypt(String username, String password) {
		return new SimpleHash(ALGORITH_NAME, password, ByteSource.Util.bytes(username + SALT), HASH_ITERATIONS).toHex();
	}

	/**
	 * MD5加密处理
	 * @param s
	 * @return
	 */
	public static final String md5(String s) {

		if (StringUtils.isBlank(s)) {
			return "";
		}

		byte[] bytes = DigestUtils.md5(s);
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			int t = bytes[i] & 0xFF;
			buffer.append(hexDigits[(t >> 4) & 0x0F]);
			buffer.append(hexDigits[t & 0x0F]);
		}
		return buffer.toString();
	}
}