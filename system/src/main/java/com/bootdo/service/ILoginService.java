package com.bootdo.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.bootdo.vo.ResponseVO;

/**
 * 后台权限系统登录处理
 * 
 * @author created by zjw on 2019年1月21日 下午9:44:58
 */
public interface ILoginService {

	/**
	 * 实现登录
	 * 
	 * @param username
	 * @param password
	 * @return ResponseVO
	 */
	ResponseVO ajaxLogin(String username, String password, String captcha, String rememberMe);

	/**
	 * 生成验证码图片
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	void genCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException;

	/**
	 * 跳转到登录页面
	 * 
	 * @param model
	 */
	void toLogin(Model model);

	/**
	 * 处理错误页面
	 * 
	 * @param model
	 * @param request
	 */
	void handleError(Model model, HttpServletRequest request);
}