package com.bootdo.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.bootdo.domain.SysUserDO;

/**
 * shiro工具类
 * 
 * @author created by zjw on 2019年1月20日 下午8:46:37
 */
public class ShiroUtils {

	public static Subject getSubjct() {
		return SecurityUtils.getSubject();
	}

	public static SysUserDO getSysUser() {
		return (SysUserDO) getSubjct().getPrincipal();
	}

	public static Long getSysUserId() {
		return getSysUser().getId();
	}

	public static Session getSession() {
		return getSubjct().getSession();
	}

	public static void logout() {
		getSubjct().logout();
	}
}