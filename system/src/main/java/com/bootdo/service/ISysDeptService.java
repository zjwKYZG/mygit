package com.bootdo.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.bootdo.domain.SysDeptDO;
import com.bootdo.validator.SysDeptDOForm;
import com.bootdo.vo.ResponseVO;

/**
 * 
 * @author created by zjw on 2019年4月6日 下午3:10:25
 */
public interface ISysDeptService {

	/**
	 * 根据查询条件获取部门列表
	 * @param params 查询条件
	 * @return List<SysDeptDO>
	 */
	List<SysDeptDO> findListByMap(Map<String, Object> params);
	
	/**
	 * 跳转到编辑页面
	 * @param id
	 * @param model
	 * @return String
	 */
	void toEdit(Long id, Model model);
	
	/**
	 * 保存添加/修改的数据
	 * @param sysDeptDO
	 * @return ResponseVO
	 */
	ResponseVO saveOrUpdate(SysDeptDOForm form);
	
	long save(SysDeptDOForm form);
	
	int update(SysDeptDOForm form);
	
	/**
	 * 跳转到详细页面
	 * @param id 部门ID
	 * @param model
	 */
	void toDetail(Long id, Model model);
	
	/**
	 * 删除部门
	 * @param id 部门ID
	 * @return ResponseVO 封装通用响应对象（VO）
	 */
	ResponseVO delete(Long id);
	
	/**
	 * 根据部门ID获取该部门及下属部门的部门ID列表
	 * @param id 部门ID
	 * @return List<Long> 该部门及下属部门的部门ID列表
	 */
	List<Long> findSysDeptIdsById(Long id);
	
	/**
	 * 根据部门ID列表批量修改部门状态, 状态 (0: 正常; 1: 冻结: -1: 删除)
	 * @param params 条件参数对象
	 */
	void batchUpdateByMap(Map<String, Object> params);
}