package com.bootdo.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.bootdo.dao.SysUserDao;
import com.bootdo.domain.SysUserDO;
import com.bootdo.enums.ResponseEnum;
import com.bootdo.exception.ResultException;
import com.bootdo.service.ISysDeptService;
import com.bootdo.service.ISysUserService;
import com.bootdo.utils.CollectionUtils;
import com.bootdo.utils.MD5Utils;
import com.bootdo.utils.StringUtils;
import com.bootdo.validator.SysUserDOForm;
import com.bootdo.vo.ResponseVO;

/**
 * 
 * @author created by zjw on 2019年1月29日 下午2:47:38
 */
@Service
public class SysUserServiceImpl implements ISysUserService { 

	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private ISysDeptService sysDeptService;

	@Override
	public List<SysUserDO> findListByMap(Map<String, Object> params) {
		return sysUserDao.findListByMap(params);
	}

	@Override
	public long findCountByMap(Map<String, Object> params) {
		return sysUserDao.findCountByMap(params);
	}

	@Override
	public int findCountBySysDeptIds(List<Long> sysDeptIds) {
		return sysUserDao.findCountBySysDeptIds(sysDeptIds);
	}
	
	@Override
	public long save(SysUserDOForm form) {
		return sysUserDao.save(form);
	}

	@Override
	public void list(Model model) {

		List<SysUserDO> list = this.findListByMap(null);
		if (CollectionUtils.isEmpty(list)) {
			return;
		}

		list.forEach(l -> {
			Map<String, Object> params = new HashMap<>();
			params.put("id", l.getSysDeptId());
			l.setDeptName(sysDeptService.findListByMap(params).get(0).getDeptName());
		});

		// 封装数据
		model.addAttribute("list", list);
	}

	@Override
	public void toEdit(Long id, Model model) {

		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		List<SysUserDO> list = this.findListByMap(params);
		SysUserDO sysUserDO = list.get(0);

		params = new HashMap<>();
		params.put("id", sysUserDO.getSysDeptId());
		sysUserDO.setDeptName(sysDeptService.findListByMap(params).get(0).getDeptName());

		// 封装数据
		model.addAttribute("user", sysUserDO);
	}

	@Override
	public ResponseVO saveOrUpdate(SysUserDOForm form) {

		// 验证数据是否合格
		// 判断用户名是否重复
		Map<String, Object> params = new HashMap<>();
		String username = form.getUsername();
		params.put("username", username);
		List<SysUserDO> list = this.findListByMap(params);
		if (CollectionUtils.isNotEmpty(list)) {
			throw new ResultException(ResponseEnum.USER_EXIST);
		}

		// 判断密码是否为空
		String password = form.getPassword();
		if (StringUtils.isBlank(password)) {
			throw new ResultException(ResponseEnum.USER_PWD_NULL);
		}

		// 判断两次密码是否一致
		if (!Objects.equals(password, form.getConfirm())) {
			throw new ResultException(ResponseEnum.USER_INEQUALITY);
		}

		// 根据用户名和密码以及shiro密码盐进行md5加密处理
		password = MD5Utils.encrypt(username, password);
		form.setPassword(password);
		
		Date date = new Date();
		form.setCreatedDate(date);
		form.setUpdatedDate(date);
		this.save(form);
		
		return ResponseVO.ok();
	}
}