package com.bootdo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据库字段状态枚举类.
 * @author created by zjw on 2019年1月29日 下午10:15:20
 */
@AllArgsConstructor
@Getter
public enum StatusEnum {

    OK((byte) 0, "正常"),
    FREEZED((byte) 1, "冻结"),
    DELETE((byte) -1, "删除");

	private Byte code;
	private String msg;
}