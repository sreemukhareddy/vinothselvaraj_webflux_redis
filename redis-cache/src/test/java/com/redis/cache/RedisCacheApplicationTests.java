package com.redis.cache;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.redisson.api.RAtomicLongReactive;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class RedisCacheApplicationTests {
	
	@Autowired
	private ReactiveStringRedisTemplate template;
	
	@Autowired
	private RedissonReactiveClient client;

	@RepeatedTest(3)
	public void contextLoads() {
		ReactiveValueOperations<String, String> valueOps = template.opsForValue();
		Mono<Void> mono = Flux.range(1, 500000)
		    .flatMap(i -> valueOps.increment("user:1:visit"))
		    .then();
		
		StepVerifier.create(mono)
		            .verifyComplete();
		
	}
	
	@RepeatedTest(3)
	public void contextLoads1() {
		RAtomicLongReactive atomicLong = this.client.getAtomicLong("user:2:visit");
		Mono<Void> mono = Flux.range(1, 500000)
		    .flatMap(i -> atomicLong.incrementAndGet())
		    .then();
		
		StepVerifier.create(mono)
		            .verifyComplete();
		
	}

}
