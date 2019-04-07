package com.bootdo.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 部门表
 * @author created by zjw on 2019年1月28日 上午11:08:10
 */
@Data
public class SysDeptDO implements Serializable {

	private static final long serialVersionUID = -8633626112497449309L;

	private Long id;          // 部门ID
	private String deptName;  // 部门名称
	private Long pid;         // 父级ID
	private String pids;      // 所有父级ID
	private Integer sort;     // 排序
	private String remark;    // 备注
	private Date createdDate; // 创建时间
	private Date updatedDate; // 修改时间
	private Byte status;      // 状态（0：正常；1：冻结；-1：删除）
}
