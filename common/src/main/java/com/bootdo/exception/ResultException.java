package com.bootdo.exception;

import com.bootdo.enums.ResponseEnum;

import lombok.Getter;

/**
 * 自定义异常
 * 
 * @author created by zjw on 2019年1月21日 下午2:46:41
 */
@Getter
public class ResultException extends RuntimeException {
	
	private static final long serialVersionUID = 2169502782770932996L;
	
	private int code;
	
	/**
     * 统一异常处理
     * @param responseEnum 错误状态枚举
     */
    public ResultException(ResponseEnum responseEnum) {
        super(responseEnum.getMsg());
        this.code = responseEnum.getCode();
    }

    /**
     * 统一异常处理
     * @param code 状态码
     * @param msg 错误信息
     */
    public ResultException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}