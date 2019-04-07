package com.bootdo.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.bootdo.domain.SysUserDO;
import com.bootdo.validator.SysUserDOForm;
import com.bootdo.vo.ResponseVO;

/**
 * 
 * @author created by zjw on 2019年1月28日 下午1:18:59
 */
public interface ISysUserService {
	
	/**
	 * 根据查询条件查询用户列表
	 * @param map 查询条件
	 * @return List<SysUserDO>
	 */
	List<SysUserDO> findListByMap(Map<String, Object> params);
	
	/**
	 * 根据查询条件查询用户数
	 * @param map
	 * @return long
	 */
	long findCountByMap(Map<String, Object> params);
	
	/**
	 * 获取员工管理列表
	 * @param model
	 * @param sysUserDO
	 * @return String
	 */
	void list(Model model);
	
	/**
	 * 跳转到编辑页面
	 * @param model
	 */
	void toEdit(Long id, Model model);
	
	/**
	 * 根据部门ID列表获取员工数
	 * @param sysDeptIds
	 * @return int
	 */
	int findCountBySysDeptIds(List<Long> sysDeptIds);
	
	/**
	 * 保存添加/修改的数据
	 * @param form
	 * @return ResponseVO
	 */
	ResponseVO saveOrUpdate(SysUserDOForm form);
	
	long save(SysUserDOForm form);
}