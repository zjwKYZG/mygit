package com.bootdo.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * 菜单表
 * @author created by zjw on 2019年1月28日 上午11:20:55
 */
@Data
public class SysMenuDO implements Serializable {
	
	private static final long serialVersionUID = 2798304003034942866L;

	private Long id;          // 菜单ID
	private String menuName;  // 菜单名称
	private Long pid;         // 父级ID
	private String url;       // URL地址
	private String authCode;  // 授权码(多个用逗号分隔，如：sys:user:user,sys:role:role)
	private String icon;      // 图标
	private Byte type;        // 类型（1：一级菜单；2：二级菜单；3：按钮）
	private Integer sort;     // 排序
	private String remark;    // 备注
	private Date createdDate; // 创建时间
	private Date updatedDate; // 修改时间
	private Byte status;      // 状态（0：正常；1：冻结；-1：删除）
	
	// 非数据库字段
	// 二级菜单Map
	private Map<Long, SysMenuDO> children = new HashMap<>();
}