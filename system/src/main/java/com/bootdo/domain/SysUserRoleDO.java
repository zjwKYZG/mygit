package com.bootdo.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户角色表
 * @author created by zjw on 2019年1月28日 下午1:06:47
 */
@Data
public class SysUserRoleDO implements Serializable {
	
	private static final long serialVersionUID = 1691329484165929169L;

	private Long id;        // 主键ID
	private Long sysUserId; // 用户ID
	private Long sysRoleId; // 角色ID
}
