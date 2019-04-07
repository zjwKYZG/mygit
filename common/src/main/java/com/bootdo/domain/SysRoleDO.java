package com.bootdo.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 角色表
 * @author created by zjw on 2019年1月28日 上午11:24:56
 */
@Data
public class SysRoleDO implements Serializable {
	
	private static final long serialVersionUID = 7431137010713067622L;

	private Long id;          // 角色ID
	private String roleName;  // 角色名称
	private String roleLogo;  // 角色标识
	private String remark;    // 备注
	private Date createdDate; // 创建时间
	private Date updatedDate; // 修改时间
	private Byte status;      // 状态（0：正常；1：冻结；-1：删除）
}
