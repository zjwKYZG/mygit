package com.bootdo.service;

import org.springframework.ui.Model;

/**
 * bootdo后台权限系统主页展示
 * 
 * @author created by zjw on 2019年1月30日 下午6:03:19
 */
public interface IHomePageService {

	/**
	 * 后台主体内容
	 * 
	 * @param model
	 * @return String
	 */
	public String mainContent(Model model);
}
