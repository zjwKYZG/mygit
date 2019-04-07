package com.bootdo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.bootdo.config.properties.ProjectProperties;

/**
 * 文件上传处理工具类
 * 
 * @author created by zjw on 2019年1月24日 下午4:55:00
 */
public class FileUploadUtils {

	/**
	 * 获取文件上传保存路径
	 */
	public static String getUploadPath() {
		ProjectProperties properties = SpringContextUtils.getBean(ProjectProperties.class);
		return properties.getFileUploadPath();
	}

	/**
	 * 获取文件上传目录的静态资源路径
	 */
	public static String getPathPattern() {
		ProjectProperties properties = SpringContextUtils.getBean(ProjectProperties.class);
		return properties.getStaticPathPattern().replace("/**", "");
	}

	/**
	 * 生成随机且唯一的文件名
	 */
	public static String genFileName(String originalFilename) {
		String fileSuffix = StringUtils.getFileSuffix(originalFilename);
		return UUID.randomUUID().toString().replace("-", "") + fileSuffix;
	}

	/**
	 * 生成指定格式的目录名称(日期格式)
	 */
	public static String genDateMkdir(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return "/" + sdf.format(new Date()) + "/";
	}
}