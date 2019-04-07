package com.bootdo.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * DruidDataSource连接池配置类
 * 
 * @author created by zjw on 2019年1月22日 下午2:19:06
 */
@Slf4j
@Configuration
public class DruidDBConfig {

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;

	@Value("${spring.datasource.initialSize}")
	private int initialSize;

	@Value("${spring.datasource.minIdle}")
	private int minIdle;

	@Value("${spring.datasource.maxActive}")
	private int maxActive;

	@Value("${spring.datasource.maxWait}")
	private int maxWait;

	@Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;

	@Value("${spring.datasource.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;

	@Value("${spring.datasource.validationQuery}")
	private String validationQuery;

	@Value("${spring.datasource.testWhileIdle}")
	private boolean testWhileIdle;

	@Value("${spring.datasource.testOnBorrow}")
	private boolean testOnBorrow;

	@Value("${spring.datasource.testOnReturn}")
	private boolean testOnReturn;

	@Value("${spring.datasource.poolPreparedStatements}")
	private boolean poolPreparedStatements;

	@Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;

	@Value("${spring.datasource.filters}")
	private String filters;

	@Value("{spring.datasource.connectionProperties}")
	private String connectionProperties;

	@Bean(initMethod = "init", destroyMethod = "close") // 声明其为Bean实例
	@Primary // 在同样的DataSource中，首先使用被标注的DataSource
	public DataSource dataSource() {

		DruidDataSource datasource = new DruidDataSource();
		datasource.setUrl(this.dbUrl);
		datasource.setUsername(this.username);
		datasource.setPassword(this.password);
		datasource.setDriverClassName(this.driverClassName);

		// configuration
		datasource.setInitialSize(this.initialSize);
		datasource.setMinIdle(this.minIdle);
		datasource.setMaxActive(this.maxActive);
		datasource.setMaxWait(this.maxWait);
		datasource.setTimeBetweenEvictionRunsMillis(this.timeBetweenEvictionRunsMillis);
		datasource.setMinEvictableIdleTimeMillis(this.minEvictableIdleTimeMillis);
		datasource.setValidationQuery(this.validationQuery);
		datasource.setTestWhileIdle(this.testWhileIdle);
		datasource.setTestOnBorrow(this.testOnBorrow);
		datasource.setTestOnReturn(this.testOnReturn);
		datasource.setPoolPreparedStatements(this.poolPreparedStatements);
		datasource.setMaxPoolPreparedStatementPerConnectionSize(this.maxPoolPreparedStatementPerConnectionSize);

		try {
			datasource.setFilters(filters);
		} catch (SQLException e) {
			log.error("druid configuration initialization filter： {}", e);
		}

		datasource.setConnectionProperties(connectionProperties);
		return datasource;
	}

	@Bean
	public ServletRegistrationBean<StatViewServlet> druidServlet() {

		ServletRegistrationBean<StatViewServlet> reg = new ServletRegistrationBean<>();
		reg.setServlet(new StatViewServlet());
		reg.addUrlMappings("/druid/*");
		reg.addInitParameter("allow", ""); // 白名单
		return reg;
	}

	@Bean
	public FilterRegistrationBean<WebStatFilter> filterRegistrationBean() {

		FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new WebStatFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		filterRegistrationBean.addInitParameter("profileEnable", "true");
		filterRegistrationBean.addInitParameter("principalCookieName", "USER_COOKIE");
		filterRegistrationBean.addInitParameter("principalSessionName", "USER_SESSION");
		filterRegistrationBean.addInitParameter("DruidWebStatFilter", "/*");
		return filterRegistrationBean;
	}
}