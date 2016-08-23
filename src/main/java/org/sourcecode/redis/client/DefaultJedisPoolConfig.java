package org.sourcecode.redis.client;

import redis.clients.jedis.JedisPoolConfig;

public class DefaultJedisPoolConfig extends JedisPoolConfig {

	public DefaultJedisPoolConfig() {
		super();
		setTestWhileIdle(true);
		setMinEvictableIdleTimeMillis(60000);// 超过一分钟的被回收
		setTimeBetweenEvictionRunsMillis(30000);// 30秒检查一次
		setNumTestsPerEvictionRun(3);// 每次回收的个数配置
		setMaxTotal(300);// 最大连接数
		setMaxIdle(10);// 最大空闲数
		setMinIdle(1);// 最小空闲数
		setMaxWaitMillis(1000);
	}

}
