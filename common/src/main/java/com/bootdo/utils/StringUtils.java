package com.bootdo.utils;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

/**
 * 字符串工具类
 * 
 * @author created by zjw on 2019年1月21日 下午2:53:36
 */
@Slf4j
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	/**
	 * 获取随机位数的字符串
	 * 
	 * @param length
	 *            随机位数
	 */
	public static String getRandomString(int length) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			// 获取ascii码中的字符 数字48-57 小写65-90 大写97-122
			int range = random.nextInt(75) + 48;
			range = range < 97 ? (range < 65 ? (range > 57 ? 114 - range : range) : (range > 90 ? 180 - range : range))
					: range;
			sb.append((char) range);
		}
		return sb.toString();
	}

	/**
	 * 首字母转小写
	 */
	public static String lowerFirst(String word) {

		if (StringUtils.isBlank(word)) {
			return "";
		}
		if (Character.isLowerCase(word.charAt(0))) {
			return word;
		} else {
			return (new StringBuilder()).append(Character.toLowerCase(word.charAt(0))).append(word.substring(1))
					.toString();
		}
	}

	/**
	 * 首字母转大写
	 */
	public static String upperFirst(String word) {

		if (StringUtils.isBlank(word)) {
			return "";
		}
		if (Character.isUpperCase(word.charAt(0))) {
			return word;
		} else {
			return (new StringBuilder()).append(Character.toUpperCase(word.charAt(0))).append(word.substring(1))
					.toString();
		}
	}

	/**
	 * 获取项目不同模式下的根路径
	 */
	public static String getProjectPath() {
		String filePath = StringUtils.class.getResource("").getPath();
		String projectPath = StringUtils.class.getClassLoader().getResource("").getPath();
		StringBuilder path = new StringBuilder();

		if (!filePath.startsWith("file:/")) {
			// 开发模式下根路径
			char[] filePathArray = filePath.toCharArray();
			char[] projectPathArray = projectPath.toCharArray();
			for (int i = 0; i < filePathArray.length; i++) {
				if (projectPathArray.length > i && filePathArray[i] == projectPathArray[i]) {
					path.append(filePathArray[i]);
				} else {
					break;
				}
			}
		} else if (!projectPath.startsWith("file:/")) {
			// 部署服务器模式下根路径
			projectPath = projectPath.replace("/WEB-INF/classes/", "");
			projectPath = projectPath.replace("/target/classes/", "");
			try {
				path.append(URLDecoder.decode(projectPath, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				return projectPath;
			}
		} else {
			// jar包启动模式下根路径
			String property = System.getProperty("java.class.path");
			int firstIndex = property.lastIndexOf(System.getProperty("path.separator")) + 1;
			int lastIndex = property.lastIndexOf(File.separator) + 1;
			path.append(property, firstIndex, lastIndex);
			log.info("生成后的路径==========================： {}", path);
		}

		File file = new File(path.toString());
		String rootPath = "/";
		try {
			rootPath = URLDecoder.decode(file.getAbsolutePath(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return rootPath.replaceAll("\\\\", "/");
	}

	/**
	 * 获取文件后缀名
	 */
	public static String getFileSuffix(String fileName) {

		if (StringUtils.isBlank(fileName)) {
			return "";
		}
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 获取具体的错误信息
	 * @param e
	 * @return String
	 */
	public static String getExceptionInfo(Exception e) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		return stringWriter.toString();
	}
}