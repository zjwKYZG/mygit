package com.bootdo.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bootdo.config.properties.ProjectProperties;
import com.bootdo.redis.shiro.RedisCacheManager;
import com.bootdo.redis.shiro.RedisManager;
import com.bootdo.redis.shiro.RedisSessionDAO;
import com.bootdo.redis.shiro.ShiroSessionFactory;
import com.bootdo.redis.shiro.ShiroSessionManager;
import com.bootdo.shiro.SysUserAuthRealm;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

/**
 * shiro配置类
 * 
 * @author created by zjw on 2019年1月20日 下午4:27:39
 */
@Configuration
public class ShiroConfig {
	
	@Autowired
	private ProjectProperties properties;

	/**
	 * 配置shiro生命周期处理器
	 */
	@Bean
	public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * 必须（thymeleaf页面使用shiro标签控制按钮是否显示）
	 * 未引入thymeleaf包，Caused by: java.lang.ClassNotFoundException: org.thymeleaf.dialect.AbstractProcessorDialect
	 */
	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}

	/**
	 * 配置shiro过滤器
	 * 
	 * @param securityManager
	 * @return ShiroFilterFactoryBean
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 必须设置 SecurityManager：shiro的核心安全接口
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		
		/**
         *  设置过滤规则（注意优先级）
         *  配置访问权限必须是LinkedHashMap，因为它必须保证有序
         *  anon：无需认证（登录）可访问
         * 	authc：必须认证（登录）才可访问，否则将会将请求重定向到配置的loginUrl地址
         * 	perms[标识]：拥有资源权限才可访问
         * 	role：拥有角色权限才可访问
         * 	user：认证（登录）和自动登录可访问
         */
		Map<String, String> filterChainDefinitionMap = shiroFilterFactoryBean.getFilterChainDefinitionMap();
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/login/verify", "anon");
		// logout是shiro提供的过滤器
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/captcha", "anon");
		filterChainDefinitionMap.put("/noAuth", "anon");
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/images/**", "anon");
		filterChainDefinitionMap.put("/lib/**", "anon");
		filterChainDefinitionMap.put("/favicon.ico", "anon");
		filterChainDefinitionMap.put("/pay/unifiedorder", "anon");
		
		// 过滤链定义，从上向下顺序执行，一般将（/**）放在最为下边===============>这是一个坑，一不小心代码就不好使了
		// 其他资源都需要认证（登录）：
		// authc（表示需要认证（登录）才能进行访问）
		// user（表示配置记住我或认证（登录）通过可以访问的地址）
		filterChainDefinitionMap.put("/**", "user");
        
        // 设置登录页面，默认重定向（/login.jsp）
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 未授权错误页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");
        
		return shiroFilterFactoryBean;
	}

	/**
	 * 配置核心安全事务管理器
	 * @return SecurityManager
	 */
	@Bean
	public SecurityManager securityManager() {
		
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 配置自定义realm
		securityManager.setRealm(this.sysUserAuthRealm());
		// 配置redis缓存
		securityManager.setCacheManager(this.redisCacheManager());
		// 配置自定义session管理，使用redis
		securityManager.setSessionManager(this.sessionManager());
		// 配置rememberMe（记住我）管理器（自动登录）
		securityManager.setRememberMeManager(this.rememberMeManager());
		return securityManager;
	}

	@Bean
	public SysUserAuthRealm sysUserAuthRealm() {
		
		SysUserAuthRealm sysUserAuthRealm = new SysUserAuthRealm();
		sysUserAuthRealm.setCachingEnabled(true);
		// 启用用户身份验证缓存，即缓存AuthenticationInfo信息，默认false
		sysUserAuthRealm.setAuthenticationCachingEnabled(true);
		// 缓存AuthenticationInfo信息的缓存名称
		sysUserAuthRealm.setAuthenticationCacheName("authenticationCache");
		// 启用用户授权缓存，即缓存AuthorizationInfo信息，默认false
		sysUserAuthRealm.setAuthorizationCachingEnabled(true);
		// 缓存AuthorizationInfo信息的缓存名称 
		sysUserAuthRealm.setAuthorizationCacheName("authorizationCache");
		return sysUserAuthRealm;
	}
	
	/**
     * 配置shiro缓存管理器
     * 需要添加到securityManager中
     * @return
     */
	@Bean
    public RedisCacheManager redisCacheManager() {
		
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(this.redisManager());
        // redis中针对不同用户缓存
        redisCacheManager.setPrincipalIdFieldName("username");
        // 用户授权信息缓存过期时间（秒）：30分钟
        redisCacheManager.setExpire(1800);
        return redisCacheManager;
    }
	
	@Bean
    public RedisManager redisManager() {
        return new RedisManager();
    }

	/**
	 * 开启shiro 注解模式
	 * 可以在controller中的方法前加上注解
	 * 如：@RequiresPermissions("index:home:page")
	 * @param securityManager
	 * @return AuthorizationAttributeSourceAdvisor
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor advisor(SecurityManager securityManager) {
		
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}
	
	/**
     * rememberMe（记住我）管理器（自动登录）
     * cookie管理对象，记住我功能，rememberMe管理器
     * @return CookieRememberMeManager 
     */
	@Bean
	public CookieRememberMeManager rememberMeManager() {
		
	    CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
	    cookieRememberMeManager.setCookie(this.rememberMeCookie());
	    // rememberMe，cookie加密的密钥：建议每个项目都不一样，默认AES算法，密钥长度（128 256 512 位）
	    cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
	    return cookieRememberMeManager;
	}
	
	/**
     * cookie对象，会话Cookie模板，默认为（JSESSIONID），问题：与servlet容器名冲突，故重新定义为rememberMe
     * @return SimpleCookie
     */
	@Bean 
	public SimpleCookie rememberMeCookie() {
		
		// 这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		// setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数，它有以下特点：
		// 设为true后，只能通过http访问，javascript无法访问
		// 防止xss读取cookie
		simpleCookie.setHttpOnly(true);
		simpleCookie.setPath("/");
		// 记住我cookie生效时间7天，单位（秒）
		simpleCookie.setMaxAge(this.properties.getRememberMeTimeout() * 24 * 60 * 60);
		return simpleCookie;
	}
	
	/**
	 * FormAuthenticationFilter过滤器：过滤记住我
	 * @return FormAuthenticationFilter
	 */
	@Bean
	public FormAuthenticationFilter formAuthenticationFilter() {
		
	    FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
	    // 对应前端的checkbox的name = rememberMe
	    formAuthenticationFilter.setRememberMeParam("rememberMe");
	    return formAuthenticationFilter;
	}
	
	/**
	 * 让某个实例的某个方法的返回值注入为Bean的实例
     * Spring静态注入
	 */
	@Bean
    public MethodInvokingFactoryBean getMethodInvokingFactoryBean() {
		
        MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
        factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        factoryBean.setArguments(new Object[]{this.securityManager()});
        return factoryBean;
    }
	
	/**
     * 配置会话ID生成器
     * @return
     */
    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }
	
    /**
     * 配置会话管理器，设定会话超时及保存
     */
    @Bean
    public SessionManager sessionManager() {
    	
        // 自定义获取SessionManager，优化单次请求需要多次访问redis的问题
    	ShiroSessionManager shiroSessionManager = new ShiroSessionManager();
    	// 配置session监听器
    	Collection<SessionListener> listeners = new ArrayList<>();
        listeners.add(new BDSessionListener());
        shiroSessionManager.setSessionListeners(listeners);
        shiroSessionManager.setSessionIdCookie(this.sessionIdCookie());
        shiroSessionManager.setSessionDAO(this.sessionDAO());
        shiroSessionManager.setCacheManager(this.redisCacheManager());
        // 全局会话超时时间（单位毫秒），默认30分钟
        shiroSessionManager.setGlobalSessionTimeout(1800000);
        // 是否启用删除无效的session对象，默认为true
        shiroSessionManager.setDeleteInvalidSessions(true);
        // 是否启用定时调度器进行检测过期session，默认为true
        shiroSessionManager.setSessionValidationSchedulerEnabled(true);
        // 设置session失效的扫描时间，清理用户直接关闭浏览器造成的孤立会话，默认为 1个小时
        // 设置该属性，就不需要设置 ExecutorServiceSessionValidationScheduler，底层也是默认自动调用ExecutorServiceSessionValidationScheduler
        shiroSessionManager.setSessionValidationInterval(3600000); 
        // 取消url后面的JSESSIONID
        shiroSessionManager.setSessionIdUrlRewritingEnabled(false);
        shiroSessionManager.setSessionFactory(this.sessionFactory());
        return shiroSessionManager;
    }
    
    @Bean
    public ShiroSessionFactory sessionFactory() {
        return new ShiroSessionFactory();
    }
	
    /**
	 * 配置保存sessionId的cookie
	 * 注意：这里的cookie不是上面的记住我cookie，记住我需要一个cookie，session管理也需要自己的cookie
	 * 默认为（JSESSIONID），问题：与servlet容器名冲突，故重新定义为sid
	 */
    @Bean
    public SimpleCookie sessionIdCookie() {
    	
    	// 这个参数是cookie的名称
    	SimpleCookie simpleCookie = new SimpleCookie("sid");
    	// setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数，它有以下特点：
    	// 设为true后，只能通过http访问，javascript无法访问
    	// 防止xss读取cookie
    	simpleCookie.setHttpOnly(true);
    	simpleCookie.setPath("/");
    	// maxAge = -1表示浏览器关闭时失效此Cookie
    	simpleCookie.setMaxAge(-1);
    	return simpleCookie;
    }
	
    /**
	 * SessionDAO的作用：是为Session提供CRUD并进行持久化的一个shiro组件
	 * MemorySessionDAO：直接在内存中进行会话维护
	 * EnterpriseCacheSessionDAO：提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话
	 */
    @Bean
    public SessionDAO sessionDAO() {
    	
    	RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(this.redisManager());
        // session在redis中的保存时间，确保过期时间长于sesion.getTimeout()，缓存过期时间（秒）：30分钟
        redisSessionDAO.setExpire(1800);
        return redisSessionDAO;
    }
}