package com.bootdo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.bootdo.redis.shiro.RedisSerializerUtils;

import redis.clients.jedis.JedisPoolConfig;

/**
 * redis配置
 * 
 * @author created by zjw on 2019年2月15日 上午10:01:40
 */
@Configuration
public class RedisConfig {

	/**
	 * redis地址
	 */
	@Value("${spring.redis.host}")
	private String host;

	/**
	 * redis端口号
	 */
	@Value("${spring.redis.port}")
	private int port;

	/**
	 * shiro的redis缓存使用的模板
	 * 
	 * @param redisConnectionFactory
	 * @return
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new RedisSerializerUtils());
		redisTemplate.setValueSerializer(new RedisSerializerUtils());
		// 开启事务
		// redisTemplate.setEnableTransactionSupport(true);
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}

	/**
	 * 配置RedisConnectionFactory连接工厂
	 * 
	 * @param jedisPoolConfig
	 * @return RedisConnectionFactory
	 */
	@Bean
	public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig) {

		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		// 配置redis地址
		redisStandaloneConfiguration.setHostName(this.host);
		// 配置redis端口号
		redisStandaloneConfiguration.setPort(this.port);
		// 获得默认的连接池构造
		// 这里需要注意的是，JedisConnectionFactory对于Standalone模式的没有（RedisStandaloneConfiguration，JedisPoolConfig）的构造函数，对此
		// 我们用JedisClientConfiguration接口的builder方法实例化一个构造器，还得类型转换
		JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcf = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration
				.builder();
		// 修改我们的连接池配置
		jpcf.poolConfig(jedisPoolConfig);
		// 通过构造器来构造jedis客户端配置
		JedisClientConfiguration jedisClientConfiguration = jpcf.build();
		return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
	}

	/**
	 * 配置JedisPoolConfig连接池
	 * 
	 * @return JedisPoolConfig
	 */
	@Bean
	public JedisPoolConfig jedisPoolConfig() {

		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		// 最大空闲数
		jedisPoolConfig.setMaxIdle(300);
		// 连接池的最大数据库连接数
		jedisPoolConfig.setMaxTotal(1000);
		// 最大建立连接等待时间
		jedisPoolConfig.setMaxWaitMillis(1000);
		// 逐出连接的最小空闲时间 默认1800000毫秒（30分钟）
		jedisPoolConfig.setMinEvictableIdleTimeMillis(300000);
		// 每次逐出检查，逐出的最大数目，如果为负数就是：1/abs(n)，默认3
		jedisPoolConfig.setNumTestsPerEvictionRun(10);
		// 逐出扫描的时间间隔（毫秒）如果为负数，则不运行逐出线程，默认（-1）
		jedisPoolConfig.setTimeBetweenEvictionRunsMillis(30000);
		// 是否在从池中取出连接前进行检验，如果检验失败，则从池中去除连接并尝试取出另一个
		jedisPoolConfig.setTestOnBorrow(true);
		// 在空闲时检查有效性，默认false
		jedisPoolConfig.setTestWhileIdle(true);
		return jedisPoolConfig;
	}
}