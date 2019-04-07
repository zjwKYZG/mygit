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

import com.bootdo.service.ISysDeptService;
import com.bootdo.validator.SysDeptDOForm;
import com.bootdo.vo.ResponseVO;

/**
 * 
 * @author created by zjw on 2019年4月6日 下午2:46:06
 */
@Controller
@RequestMapping("/sys/dept")
public class DeptController {

	@Autowired
	private ISysDeptService sysDeptService;

	/**
	 * 跳转到列表页面
	 */
	@GetMapping("/index")
	@RequiresPermissions("dept:index")
	public String index() {
		return "system/dept/index";
	}

	/**
	 * 部门树形数据列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public ResponseVO list() {
		return ResponseVO.ok(sysDeptService.findListByMap(null));
	}

	/**
	 * 跳转到添加页面
	 */
	@GetMapping("/add")
	public String toAdd() {
		return "system/dept/add";
	}

	/**
	 * 跳转到编辑页面
	 */
	@GetMapping("/edit/{id}")
	public String toEdit(@PathVariable("id") Long id, Model model) {
		sysDeptService.toEdit(id, model);
		return "system/dept/add";
	}

	/**
	 * 保存添加/修改的数据
	 * 
	 * @param form 表单验证对象
	 * @return ResponseVO
	 */
	@PostMapping("/saveorupdate")
	@ResponseBody
	public ResponseVO saveOrUpdate(@Validated SysDeptDOForm form) {
		return sysDeptService.saveOrUpdate(form);
	}

	/**
	 * 跳转到详细页面
	 */
	@GetMapping("/detail/{id}")
	public String toDetail(@PathVariable("id") Long id, Model model) {
		sysDeptService.toDetail(id, model);
		return "system/dept/detail";
	}
	
	/**
	 * 删除部门
	 */
	@GetMapping("/delete/{id}")
	@ResponseBody
	public ResponseVO delete(@PathVariable("id") Long id) {
		return sysDeptService.delete(id);
	}
}