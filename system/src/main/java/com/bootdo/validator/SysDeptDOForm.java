package com.bootdo.validator;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.bootdo.domain.SysDeptDO;

import lombok.Data;

/**
 * 
 * @author created by zjw on 2019年4月7日 下午5:14:59
 */
@Data
public class SysDeptDOForm extends SysDeptDO {

	private static final long serialVersionUID = -9217424499156167992L;

	@NotEmpty(message = "部门名称不能为空！")
	private String deptName;

	@NotNull(message = "父级部门不能为空！")
	private Long pid;
}