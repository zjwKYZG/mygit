package com.bootdo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bootdo.utils.FileUploadUtils;

/**
 * 静态资源路径配置类
 * 
 * @author created by zjw on 2019年1月24日 下午4:52:21
 */
@Configuration
public class UploadFilePathConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler(FileUploadUtils.getPathPattern())
				.addResourceLocations("file:" + FileUploadUtils.getUploadPath());
	}
}
