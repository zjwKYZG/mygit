package com.bootdo.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.bootdo.dao.SysDeptDao;
import com.bootdo.domain.SysDeptDO;
import com.bootdo.enums.ResponseEnum;
import com.bootdo.enums.StatusEnum;
import com.bootdo.exception.ResultException;
import com.bootdo.service.ISysDeptService;
import com.bootdo.service.ISysUserService;
import com.bootdo.utils.CollectionUtils;
import com.bootdo.validator.SysDeptDOForm;
import com.bootdo.vo.ResponseVO;

/**
 * 
 * @author created by zjw on 2019年4月6日 下午3:11:12
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService {  

	@Autowired
	private SysDeptDao sysDeptDao;
	@Autowired
	private ISysUserService sysUserService;

	@Override
	public List<SysDeptDO> findListByMap(Map<String, Object> params) {
		return sysDeptDao.findListByMap(params);
	}

	@Override
	public long save(SysDeptDOForm form) {
		return sysDeptDao.save(form);
	}

	@Override
	public int update(SysDeptDOForm form) {
		return sysDeptDao.update(form);
	}
	
	@Override
	public List<Long> findSysDeptIdsById(Long id) {
		return sysDeptDao.findSysDeptIdsById(id);
	}
	
	@Override
	public void batchUpdateByMap(Map<String, Object> params) {
		sysDeptDao.batchUpdateByMap(params);
	}

	@Override
	public void toEdit(Long id, Model model) {

		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		SysDeptDO sysDeptDO = this.findListByMap(params).get(0);
		model.addAttribute("dept", sysDeptDO);

		params = new HashMap<>();
		params.put("id", sysDeptDO.getPid());
		List<SysDeptDO> list = this.findListByMap(params);
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		model.addAttribute("pDept", list.get(0));
	}

	@Override
	public ResponseVO saveOrUpdate(SysDeptDOForm form) {
		
		Long id = form.getId();
		Long pid = form.getPid();
		Date date = new Date();
		form.setUpdatedDate(date);
		// 添加
		if (Objects.isNull(id) || id.longValue() == 0L) {
			Map<String, Object> params = new HashMap<>();
			params.put("id", pid);
			SysDeptDO sysDeptDO = this.findListByMap(params).get(0);
			form.setPids(sysDeptDO.getPid().longValue() == 0L 
					? "[" + pid + "]" : sysDeptDO.getPids() + "," + "[" + pid + "]");
			form.setCreatedDate(date);
			this.save(form);
		} else {
			// 编辑
			Map<String, Object> params = new HashMap<>();
			params.put("id", pid);
			SysDeptDO sysDeptDO = this.findListByMap(params).get(0);
			form.setPids(sysDeptDO.getPid().longValue() == 0L 
					? "[" + pid + "]" : sysDeptDO.getPids() + "," + "[" + pid + "]");
			this.update(form);
		}
		return ResponseVO.ok();
	}

	@Override
	public void toDetail(Long id, Model model) {
		
        Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		model.addAttribute("dept", this.findListByMap(params).get(0));
	}

	@Override
	public ResponseVO delete(Long id) {
		
		List<Long> sysDeptIds = this.findSysDeptIdsById(id);
		int count = sysUserService.findCountBySysDeptIds(sysDeptIds);
		if (count > 0) {
			throw new ResultException(ResponseEnum.NO_ALLOW_DELETE_SYS_DEPT);
		}
		
		// 根据部门ID列表批量删除部门
		// 状态 (0: 正常; 1: 冻结: -1: 删除)
		Map<String, Object> params = new HashMap<>();
		params.put("sysDeptIds", sysDeptIds);
		params.put("updatedDate", new Date());
		params.put("status", StatusEnum.DELETE.getCode());
		this.batchUpdateByMap(params);
		
		return ResponseVO.ok();
	}
}