package com.bootdo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误状态枚举类.
 * @author created by zjw on 2019年1月21日 下午9:37:06
 */
@AllArgsConstructor
@Getter
public enum ResponseEnum{

    /**
     * 账户问题.
     */
    USER_EXIST(401, "该用户名已经存在！"),
    USER_PWD_NULL(402, "密码不能为空！"),
    USER_INEQUALITY(403, "两次密码不一致！"),
    USER_OLD_PWD_ERROR(404, "原来密码不正确！"),
    USER_NAME_PWD_NULL(405, "用户名或密码不能为空！"),
    USER_NAME_PWD_ERROR(406, "用户名或密码错误！"),
    USER_CAPTCHA_ERROR(406, "验证码错误！"),

    /**
     * 非法操作.
     */
    STATUS_ERROR(401, "非法操作：状态有误！"),

    /**
     * 权限问题.
     */
    NO_PERMISSIONS(401, "权限不足！"),
    NO_ADMIN_AUTH(500, "不允许操作超级管理员！"),
    NO_ADMIN_STATUS(501, "不能修改超级管理员状态！"),
    NO_ADMINROLE_AUTH(500, "不允许操作管理员角色！"),
    NO_ADMINROLE_STATUS(501, "不能修改管理员角色状态！"),

    /**
     * 文件操作.
     */
    NO_FILE_NULL(401, "文件不能为空！"),
    NO_FILE_TYPE(402, "不支持该文件类型！"),
	
	/**
	 * 通用前台错误状态.
	 */
    FRONT_ERROR(400, "请求参数错误！"),
	
	/**
	 * 通用后台错误状态.
	 */
	SERVER_INTERNAL_ERROR(500, "服务器内部错误！"),
	
	/**
	 * 账号已被锁定，请联系管理员.
	 */
	USER_NAME_LOCKED(401, "账号已被锁定，请联系管理员！"),
	
	/**
	 * 实现并发登录控制.
	 * 您的账号在另一台设备上登录，如非本人操作，请立即修改密码.
	 * 重定向到login.
	 */
	USER_KICKOUT_SESSION(302, "您的账号在另一台设备上登录，如非本人操作，请立即修改密码！"),
	
	/**
	 * 当前用户未授权！.
	 */
	USER_UNAUTHORIZED(403, "当前用户未授权！"),
	
	/**
	 * 该部门及下属部门包含员工！不允许删除该部门！
	 */
	NO_ALLOW_DELETE_SYS_DEPT(400, "该部门及下属部门包含员工！不允许删除该部门！");
	
    private Integer code;
    private String msg;
}