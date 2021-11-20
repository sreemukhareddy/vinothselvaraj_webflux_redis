package com.vinsguru.redis.config;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.redisson.api.RAtomicLongReactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec03NumberTest extends BaseTest{ 
	
	@Test
	public void keyValueIncreaseTest() {
		RAtomicLongReactive atomicLong = this.redissonReactiveClient.getAtomicLong("user:1:visit");
		Mono<Void> increment =  Flux.range(1, 30)
		    .delayElements(Duration.ofSeconds(1))
		    .flatMap(i -> atomicLong.incrementAndGet())
		    .doOnNext(System.out::println)
		    .then();
		StepVerifier.create(increment)
		            .verifyComplete();
	}

}
