package com.vinsguru.redis.config;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.redisson.api.RedissonReactiveClient;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {
	
	private ReddisonConfig reddisonConfig = new ReddisonConfig();
	
	protected RedissonReactiveClient redissonReactiveClient;
	
	@BeforeAll
	public void setClient() {
		this.redissonReactiveClient = reddisonConfig.getRedissonReactiveClient();
	}
	
	@AfterAll
	public void shutDown() {
		this.redissonReactiveClient.shutdown();
	}
	
	public void sleep(long milli_seconds) {
		try {
			Thread.sleep(milli_seconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
