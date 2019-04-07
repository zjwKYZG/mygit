package com.bootdo.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bootdo.enums.ResponseEnum;
import com.bootdo.utils.SpringContextUtils;
import com.bootdo.vo.ResponseVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局统一异常处理
 * 
 * @author created by zjw on 2019年1月21日 下午2:49:45
 */
@Slf4j
@RestControllerAdvice
public class ResultExceptionHandler {

	// 拦截自定义异常
	@ExceptionHandler(ResultException.class)
	public ResponseVO handlerResultException(ResultException e) {
		return ResponseVO.error(e.getCode(), e.getMessage());
	}

	// 拦截表单验证异常
	@ExceptionHandler(BindException.class)
	public ResponseVO handlerBindException(BindException e) {
		BindingResult b = e.getBindingResult();
		return ResponseVO.error(b.getFieldError().getDefaultMessage());
	}

	// 拦截访问权限异常
	@ExceptionHandler(AuthorizationException.class)
	public ResponseVO handlerAuthorizationException(AuthorizationException e, HttpServletRequest request,
			HttpServletResponse response) {

		// 获取异常信息
		Throwable t = e.getCause();
		String msg = t.getMessage();
		Class<ResponseVO> c = ResponseVO.class;

		// 判断无权限访问的方法返回对象是否为ResponseVO
		if (!msg.contains(c.getName())) {
			try {
				// 重定向到权限不足页面
				String contextPath = request.getContextPath();
				ShiroFilterFactoryBean shiroFilter = SpringContextUtils.getBean(ShiroFilterFactoryBean.class);
				response.sendRedirect(contextPath + shiroFilter.getUnauthorizedUrl());
			} catch (IOException exception) {
				return ResponseVO.error(ResponseEnum.NO_PERMISSIONS);
			}
		}
		return ResponseVO.error(ResponseEnum.NO_PERMISSIONS);
	}

	// 拦截未知异常
	@ExceptionHandler(Exception.class)
	public ResponseVO handlerException(Exception e) {
		log.error("【系统异常】", e);
		return ResponseVO.error("未知错误：EX4399！");
	}
}