package com.vinsguru.redis.config;

import org.junit.jupiter.api.Test;
import org.redisson.api.RListReactive;
import org.redisson.api.RQueueReactive;
import org.redisson.client.codec.LongCodec;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec09ListStackQueueTest extends BaseTest{

	@Test
	public void listTest() {
		RListReactive<Long> list = this.redissonReactiveClient.getList("number-input", LongCodec.INSTANCE);
		
		Mono<Void> mono = Flux.range(1, 10)
		    .map(Long::valueOf)
		    .flatMap(list::add)
		    .then();
		
		StepVerifier.create(mono)
		            .verifyComplete();
		
		StepVerifier.create(list.size())
		            .expectNext(10)
		            .verifyComplete();
		    
	}
	
	@Test
	public void queueTest() {
		RQueueReactive<Long> queue = this.redissonReactiveClient.getQueue("number-input", LongCodec.INSTANCE);
		
		Mono<Void> mono = Flux.range(1, 10)
		    .map(Long::valueOf)
		    .flatMap(queue::add)
		    .then();
		
		StepVerifier.create(mono)
		            .verifyComplete();
		
		
		queue.poll().repeat(3).subscribe(System.out::println);
		
		    
	}
	
	@Test
	public void stackTest() {
		RQueueReactive<Long> stack = this.redissonReactiveClient.getDeque("number-input", LongCodec.INSTANCE);
		
		Mono<Void> mono = Flux.range(1, 10)
		    .map(Long::valueOf)
		    .flatMap(stack::add)
		    .then();
		
		StepVerifier.create(mono)
		            .verifyComplete();
		
		
		
		stack.poll().repeat(3).subscribe(System.out::println);
		
		    
	}
}
