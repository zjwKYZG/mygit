package com.bootdo.utils;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author bootdo 1992lcg@163.com
 */
@AllArgsConstructor
@Data
public class PageUtils implements Serializable {
	
	private static final long serialVersionUID = 7799500957958917382L;
	
	private int total;
	private List<?> rows;
}
