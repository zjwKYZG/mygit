package com.bootdo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.service.ILoginService;
import com.bootdo.vo.ResponseVO;

/**
 * bootdo后台权限系统登录处理
 * 
 * @author created by zjw on 2019年1月21日 下午1:47:48
 */
@Controller
public class LoginController implements ErrorController {

	@Autowired
	private ILoginService loginService;

	/**
	 * 生成验证码图片
	 */
	@GetMapping("/captcha")
	public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
		loginService.genCaptcha(request, response);
	}

	/**
	 * 跳转到登录页面
	 */
	@GetMapping("/login")
	public String toLogin(Model model) {
		loginService.toLogin(model);
		return "login";
	}

	/**
	 * 实现登录
	 * 
	 * @param username
	 * @param password
	 * @return ResponseVO
	 */
	@PostMapping("/login/verify")
	@ResponseBody
	public ResponseVO ajaxLogin(String username, String password, String captcha, String rememberMe) {
		return loginService.ajaxLogin(username, password, captcha, rememberMe);
	}

	/**
	 * 自定义错误页面
	 */
	@Override
	public String getErrorPath() {
		return "error";
	}

	/**
	 * 处理错误页面
	 */
	@RequestMapping("/error")
	public String handleError(Model model, HttpServletRequest request) {
		loginService.handleError(model, request);
		return "system/main/error";
	}

	/**
	 * 权限不足页面
	 */
	@GetMapping("/noAuth")
	public String noAuth() {
		return "system/main/no_auth";
	}
}