package com.bootdo.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 用户表
 * @author created by zjw on 2019年1月28日 下午1:02:20
 */
@Data
public class SysUserDO implements Serializable {

	private static final long serialVersionUID = -7982056866881037836L;

	private Long id;          // 用户ID
	private String username;  // 用户名
	private String nickname;  // 用户昵称
	private String password;  // 密码
	private Byte isAdmin;     // 1：管理员；0：非管理员
	private Long sysDeptId;   // 部门ID
	private String headPic;   // 头像
	private Byte sex;         // 性别（0：女；1：男）
	private String email;     // 邮箱
	private String mobile;    // 手机号
	private String remark;    // 备注
	private Date createdDate; // 创建时间
	private Date updatedDate; // 修改时间
	private Byte status;      // 状态（0：正常；1：冻结；-1：删除）
	
	// 非数据库字段
	List<SysRoleDO> sysRoles; // 用户角色列表
	private String deptName;  // 部门名称
}