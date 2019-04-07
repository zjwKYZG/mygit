package com.bootdo.vo;

import java.util.HashMap;
import java.util.Map;

import com.bootdo.enums.ResponseEnum;

/**
 * 封装通用响应对象（VO）
 * 
 * @author created by zjw on 2019年1月21日 下午1:59:32
 */
public class ResponseVO extends HashMap<String, Object> {

	private static final long serialVersionUID = 1699843460028413979L;

	@Override
	public ResponseVO put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public ResponseVO() {
		this.put("code", 200);
		this.put("success", true);
		this.put("msg", "操作成功！");
		this.put("data", null);
	}

	public static ResponseVO ok() {
		return new ResponseVO();
	}

	public static ResponseVO ok(String msg) {
		ResponseVO responseVO = new ResponseVO();
		responseVO.put("msg", msg);
		return responseVO;
	}

	public static ResponseVO ok(Map<String, Object> params) {
		ResponseVO responseVO = new ResponseVO();
		responseVO.putAll(params);
		return responseVO;
	}

	public static ResponseVO ok(Object data) {
		ResponseVO responseVO = new ResponseVO();
		responseVO.put("data", data);
		return responseVO;
	}

	public static ResponseVO error() {
		return ResponseVO.error(500, "操作失败！");
	}

	public static ResponseVO error(String msg) {
		return ResponseVO.error(500, msg);
	}

	public static ResponseVO error(int code, String msg) {
		ResponseVO responseVO = new ResponseVO();
		responseVO.put("code", code);
		responseVO.put("msg", msg);
		responseVO.put("success", false);
		return responseVO;
	}

	public static ResponseVO error(ResponseEnum responseEnum) {
		ResponseVO responseVO = new ResponseVO();
		responseVO.put("code", responseEnum.getCode());
		responseVO.put("msg", responseEnum.getMsg());
		responseVO.put("success", false);
		return responseVO;
	}
}