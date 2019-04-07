package com.bootdo.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * 角色菜单表
 * @author created by zjw on 2019年1月28日 上午11:27:34
 */
@Data
public class SysRoleMenuDO implements Serializable {
	
	private static final long serialVersionUID = -3431158291755637719L;

	private Long id;        // 主键ID
	private Long sysRoleId; // 角色ID
	private Long sysMenuId; // 菜单ID
}
