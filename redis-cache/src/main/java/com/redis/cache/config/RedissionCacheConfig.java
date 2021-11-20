package com.redis.cache.config;


import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissionCacheConfig {
	
	@Bean
	public CacheManager cacheManager(RedissonClient redissonClient) {
		return new RedissonSpringCacheManager(redissonClient);
	}

}
