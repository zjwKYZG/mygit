package com.bootdo.service;

import java.util.List;

import com.bootdo.domain.SysRoleDO;

/**
 * 
 * @author created by zjw on 2019年1月29日 下午5:36:45
 */
public interface ISysRoleService {

	/**
	 * 根据用户ID查询该用户角色列表
	 * @param sysUserId
	 * @return List<SysRoleDO>
	 */
	List<SysRoleDO> findListBySysUserId(Long sysUserId);
}
