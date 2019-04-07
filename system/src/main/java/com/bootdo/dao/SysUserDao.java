package com.bootdo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bootdo.domain.SysUserDO;
import com.bootdo.validator.SysUserDOForm;

/**
 * 
 * @author created by zjw on 2019年1月28日 下午1:45:23
 */
@Mapper
public interface SysUserDao {
	
	/**
	 * 根据查询条件获取用户列表
	 * @param params 查询条件
	 * @return List<SysUserDO>
	 */
	List<SysUserDO> findListByMap(Map<String, Object> params);
	
	/**
	 * 根据查询条件查询用户数
	 * @param params 查询条件
	 * @return
	 */
	long findCountByMap(Map<String, Object> params);
	
	/**
	 * 根据部门ID列表获取员工数
	 * @param sysDeptIds
	 * @return int
	 */
	int findCountBySysDeptIds(List<Long> sysDeptIds);
	
	long save(SysUserDOForm form);
}