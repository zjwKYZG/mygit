package com.bootdo.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bootdo.service.IHomePageService;

/**
 * bootdo后台权限系统主页展示
 * 
 * @author created by zjw on 2019年1月29日 下午10:50:22
 */
@Controller
public class HomePageController {

	@Autowired
	private IHomePageService homePageService;

	/**
	 * 后台主体内容
	 */
	@GetMapping("/")
	@RequiresPermissions("home:page:list")
	public String mainContent(Model model) {
		return homePageService.mainContent(model);
	}
}