package com.bootdo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bootdo.domain.SysRoleDO;

/**
 * 
 * @author created by zjw on 2019年1月29日 下午4:40:01
 */
@Mapper
public interface SysRoleDao {
	
	/**
	 * 根据用户ID查询该用户角色列表
	 * @param sysUserId
	 * @return List<SysRoleDO>
	 */
	List<SysRoleDO> findListBySysUserId(Long sysUserId);
}
