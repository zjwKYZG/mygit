package com.bootdo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bootdo.domain.SysUserRoleDO;

/**
 * 
 * @author created by zjw on 2019年1月29日 下午4:13:14
 */
@Mapper
public interface SysUserRoleDao {
	
	/**
	 * 根据查询条件查询用户列表
	 * @param params
	 * @return List<SysUserRoleDO>
	 */
	List<SysUserRoleDO> findListByMap(Map<String, Object> params);
}
