package com.bootdo.service.impl;

import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.bootdo.config.properties.ProjectProperties;
import com.bootdo.enums.ResponseEnum;
import com.bootdo.exception.ResultException;
import com.bootdo.service.ILoginService;
import com.bootdo.utils.CaptchaUtils;
import com.bootdo.utils.MD5Utils;
import com.bootdo.utils.ShiroUtils;
import com.bootdo.utils.SpringContextUtils;
import com.bootdo.utils.StringUtils;
import com.bootdo.vo.ResponseVO;

/**
 * 后台权限系统登录处理
 * 
 * @author created by zjw on 2019年1月21日 下午9:45:48
 */
@Service
public class LoginServiceImpl implements ILoginService {

	@Autowired
	private ProjectProperties projectProperties;

	@Override
	public ResponseVO ajaxLogin(String username, String password, String captcha, String rememberMe) {

		// 判断用户名或密码是否为空
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			throw new ResultException(ResponseEnum.USER_NAME_PWD_NULL);
		}

		// 判断验证码是否正确
		if (projectProperties.isCaptchaOpen()) {
			Session session = ShiroUtils.getSession();
			String code = (String) session.getAttribute("captcha");
			if (StringUtils.isBlank(captcha) || StringUtils.isBlank(code)
					|| !Objects.equals(captcha.toUpperCase(), code.toUpperCase())) {
				throw new ResultException(ResponseEnum.USER_CAPTCHA_ERROR);
			}
			session.removeAttribute("captcha");
		}

		// 1：根据用户名和密码以及shiro密码盐进行md5加密处理
		password = MD5Utils.encrypt(username, password);

		// 2：封装用户数据
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);

		// 3：获取Subject主体对象
		Subject subject = ShiroUtils.getSubjct();

		// 4：判断是否自动登录（记住我）
		token.setRememberMe(StringUtils.isNotBlank(rememberMe) ? true : false);

		try {
			// 5：执行登录，进入自定义用户Realm（SysUserAuthRealm.doGetAuthenticationInfo()）
			subject.login(token);
			return ResponseVO.ok();
		} catch (AuthenticationException e) {
			throw new ResultException(ResponseEnum.USER_NAME_PWD_ERROR);
		}
	}

	@Override
	public void genCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// 设置响应头信息，通知浏览器不要缓存
		response.setHeader("Expires", "-1");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "-1");
		response.setContentType("image/jpeg");

		// 获取验证码
		String code = CaptchaUtils.getRandomCode();
		// 将验证码输入到session中，用来验证
		request.getSession().setAttribute("captcha", code);
		// 输出到web页面
		ImageIO.write(CaptchaUtils.genCaptcha(code), "jpg", response.getOutputStream());
	}

	@Override
	public void toLogin(Model model) {

		ProjectProperties properties = SpringContextUtils.getBean(ProjectProperties.class);
		model.addAttribute("isCaptcha", properties.isCaptchaOpen());
	}

	@Override
	public void handleError(Model model, HttpServletRequest request) {

		int statusCode = (int) request.getAttribute("javax.servlet.error.status_code");
		String errorMsg = "好像出错了呢！";
		if (statusCode == 404) {
			errorMsg = "页面找不到了！好像是去火星了~~~";
		}
		model.addAttribute("statusCode", statusCode);
		model.addAttribute("msg", errorMsg);
	}
}