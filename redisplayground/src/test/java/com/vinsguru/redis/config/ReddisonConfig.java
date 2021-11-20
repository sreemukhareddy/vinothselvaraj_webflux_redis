package com.vinsguru.redis.config;

import java.util.Objects;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.config.Config;

public class ReddisonConfig {
	
	private RedissonClient redissonClient;
	
	public RedissonClient getRedissonClient() {
		if(Objects.isNull(redissonClient)) {
			Config config = new Config();
			config.useSingleServer()
			      .setAddress("redis://127.0.0.1:6379");
			redissonClient = Redisson.create(config);
		}
		return redissonClient;
	}
	
	public RedissonReactiveClient getRedissonReactiveClient() {
		return getRedissonClient().reactive();
	}

}
