package com.bootdo.utils;

import java.util.Objects;
import java.util.Random;

/**
 * 随机数工具类
 * 
 * @author created by zjw on 2019年2月20日 下午2:18:57
 */
public class RandomUtils {
	
	private static RandomUtils instance;
	private static Random random;

	public static RandomUtils getInstance() {
		synchronized (RandomUtils.class) {
			if (Objects.isNull(instance)) {
				instance = new RandomUtils();
				random = new Random();
			}
		}
		return instance;
	}

	/**
	 * 获取指定范围内的随机数（0 ~ limit - 1）
	 * @param limit
	 * @return int
	 */
	public int getNumByLimit(Integer limit) {
		return random.nextInt(limit);
	}

	/**
	 * 获取指定位数的随机数字符串
	 * @param count 指定位数
	 * @return String
	 */
	public String getRandomStrByCount(int count) {
		
		if (count == 0) {
			return "";
		}
		
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < count; i++) {
			buffer.append(this.getNumByLimit(10));
		}
		return buffer.toString();
	}
}