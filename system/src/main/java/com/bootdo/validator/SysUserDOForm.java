package com.bootdo.validator;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bootdo.domain.SysUserDO;

import lombok.Data;

@Data
public class SysUserDOForm extends SysUserDO {

	private static final long serialVersionUID = -6541724256146332797L;
	
	@NotEmpty(message = "用户名不能为空！")
    private String username;
	
    @NotEmpty(message = "用户昵称不能为空！")
    @Size(min = 2, message = "用户昵称：请输入至少2个字符！")
    private String nickname;
    
    private String confirm;
    
    @NotNull(message = "所在部门不能为空！")
	private Long sysDeptId;
}