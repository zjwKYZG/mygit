package com.bootdo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据库菜单表资源类型枚举类.
 * @author created by zjw on 2019年1月31日 上午10:35:48
 */
@AllArgsConstructor
@Getter
public enum SysMenuTypeEnum {
	
	TOP_LEVEL((byte) 1, "一级菜单"),
	SUB_LEVEL((byte) 2, "二级菜单"),
	BUTTON((byte) 3, "按钮");
	
	private Byte type;
	private String msg;
}