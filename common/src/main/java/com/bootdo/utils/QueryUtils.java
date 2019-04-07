package com.bootdo.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * 查询参数
 */
@Getter
@Setter
public class QueryUtils extends LinkedHashMap<String, Object> {
	
	private static final long serialVersionUID = 4232217100548170817L;
	
	// 
	private int offset;
	// 每页条数
	private int limit;

	public QueryUtils(Map<String, Object> params) {
		this.putAll(params);
		// 分页参数
		this.offset = Integer.parseInt(params.get("offset").toString());
		this.limit = Integer.parseInt(params.get("limit").toString());
		this.put("offset", offset);
		this.put("page", offset / limit + 1);
		this.put("limit", limit);
	}

	public void setOffset(int offset) {
		this.put("offset", offset);
	}
}