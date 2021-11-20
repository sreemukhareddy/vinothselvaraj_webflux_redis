package com.vinsguru.redis.config;

import java.time.Duration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBlockingQueueReactive;
import org.redisson.client.codec.LongCodec;

import reactor.core.publisher.Flux;

public class Lec10MessageQueueTest extends BaseTest {
	
	RBlockingQueueReactive<Long> msgQueue;
	
	@BeforeAll
	public void setupMessageQueue() {
		this.msgQueue = this.redissonReactiveClient.getBlockingQueue("message-queue", LongCodec.INSTANCE);
	}
	
	@Test
	public void consumer() {
		this.msgQueue.takeElements()
		             .delayElements(Duration.ofMillis(500))
		             .doOnNext(i -> System.out.println("Consumer received " + i))
		             .subscribe();
		
		sleep(60000);
	}
	
	@Test
	public void producer() {
		Flux.range(1, 1000)
		    .delayElements(Duration.ofMillis(500))
		    .map(Long::valueOf)
		    .flatMap(i -> {
		    	return this.msgQueue.add(i);
		    })
		    .subscribe();
		
		sleep(60000);
	}

}
