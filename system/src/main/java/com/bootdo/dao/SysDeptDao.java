package com.bootdo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bootdo.domain.SysDeptDO;
import com.bootdo.validator.SysDeptDOForm;

/**
 * 
 * @author created by zjw on 2019年4月6日 下午3:02:08
 */
@Mapper
public interface SysDeptDao {

	/**
	 * 根据查询条件获取部门列表
	 * @param params 查询条件
	 * @return List<SysDeptDO>
	 */
	List<SysDeptDO> findListByMap(Map<String, Object> params);
	
	long save(SysDeptDOForm form);
	
	int update(SysDeptDOForm form);
	
	/**
	 * 根据部门ID获取该部门及下属部门的部门ID列表
	 * @param id 部门ID
	 * @return List<Long> 该部门及下属部门的部门ID列表
	 */
	List<Long> findSysDeptIdsById(@Param("id") Long id);
	
	/**
	 * 根据部门ID列表批量修改部门状态, 状态 (0: 正常; 1: 冻结: -1: 删除)
	 * @param params 条件参数对象
	 */
	void batchUpdateByMap(Map<String, Object> params);
}