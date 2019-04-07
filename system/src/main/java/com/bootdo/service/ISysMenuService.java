package com.bootdo.service;

import java.util.List;

import com.bootdo.domain.SysMenuDO;

/**
 * 
 * @author created by zjw on 2019年1月29日 下午9:59:00
 */
public interface ISysMenuService {

	/**
	 * 根据角色ID列表查询角色所对应的菜单资源列表
	 * @param sysRoleIds
	 * @return List<SysMenuDO>
	 */
	List<SysMenuDO> findListBySysRoleIds(List<Long> sysRoleIds);
}
