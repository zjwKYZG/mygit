package com.bootdo.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.bootdo.utils.StringUtils;

import lombok.Data;

/**
 * 项目属性配置类
 * 
 * @author created by zjw on 2019年1月24日 下午4:04:53
 */
@Data
@ConfigurationProperties(prefix = "project")
@Component
public class ProjectProperties {

	// 是否开启验证码
	private boolean captchaOpen = false;
	// 上传文件路径
	private String fileUploadPath;
	// 上传文件静态访问路径
	private String staticPathPattern = "/upload/**";
	// cookie记住登录信息时间，默认7天
	private int rememberMeTimeout = 7;
	// session会话超时时间，默认30分钟
	private int globalSessionTimeout = 1800;
	// session会话检测间隔时间，默认15分钟
	private int sessionValidationInterval = 900;

	// xss防护设置
	// xss防护开关
	private boolean xssEnabled = true;
	// 拦截规则，可通过“,”隔开多个
	private String xssUrlPatterns = "/*";
	// 忽略规则，可通过“,”隔开多个
	private String xssExcludes = "/css/*,/images/*,/js/*,/lib/*,/favicon.ico";

	public String getFileUploadPath() {

		if (StringUtils.isBlank(fileUploadPath)) {
			return StringUtils.getProjectPath() + "/upload/";
		}
		return fileUploadPath;
	}
}