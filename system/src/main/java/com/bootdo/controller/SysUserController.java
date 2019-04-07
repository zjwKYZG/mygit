package com.bootdo.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.service.ISysUserService;
import com.bootdo.validator.SysUserDOForm;
import com.bootdo.vo.ResponseVO;

/**
 * 
 * @author created by zjw on 2019年2月18日 下午4:02:44
 */
@Controller
@RequestMapping("/sys/user")
public class SysUserController {

	@Autowired
	private ISysUserService sysUserService;

	/**
	 * 获取员工管理列表
	 * 
	 * @param model
	 * @param sysUserDO
	 * @return String
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:user:list")
	public String list(Model model) {
		sysUserService.list(model);
		return "system/user/index";
	}

	/**
	 * 跳转到添加页面
	 */
	@GetMapping("/add")
	public String toAdd() {
		return "system/user/add";
	}

	/**
	 * 跳转到编辑页面
	 */
	@GetMapping("/edit/{id}")
	public String toEdit(@PathVariable("id") Long id, Model model) {
		sysUserService.toEdit(id, model);
		return "system/user/add";
	}

	/**
	 * 保存添加/修改的数据
	 * 
	 * @param form 表单验证对象
	 * @return ResponseVO
	 */
	@PostMapping("/saveorupdate")
	@ResponseBody
	public ResponseVO saveOrUpdate(@Validated SysUserDOForm form) {
		return sysUserService.saveOrUpdate(form);
	}
}