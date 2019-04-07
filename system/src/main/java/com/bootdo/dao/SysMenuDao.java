package com.bootdo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bootdo.domain.SysMenuDO;

/**
 * 
 * @author created by zjw on 2019年1月29日 下午9:33:53
 */
@Mapper
public interface SysMenuDao {
	
	/**
	 * 根据角色ID列表查询角色所对应的菜单资源列表
	 * @param sysRoleIds
	 * @return List<SysMenuDO>
	 */
	List<SysMenuDO> findListBySysRoleIds(List<Long> sysRoleIds);
}
