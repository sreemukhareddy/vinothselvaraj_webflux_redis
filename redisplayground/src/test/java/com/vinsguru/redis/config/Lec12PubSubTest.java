package com.vinsguru.redis.config;

import org.junit.jupiter.api.Test;
import org.redisson.api.RTopicReactive;
import org.redisson.client.codec.StringCodec;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec12PubSubTest extends BaseTest{
	
	@Test
	public void subscriber1() {
		
		RTopicReactive topic = this.redissonReactiveClient.getTopic("slackhome", StringCodec.INSTANCE);
		
		topic.getMessages(String.class)
		     .doOnNext(System.out::println)
		     .subscribe();
		
		sleep(60000);
	}
	
	@Test
	public void subscriber2() {
		RTopicReactive topic = this.redissonReactiveClient.getTopic("slackhome", StringCodec.INSTANCE);
		
		topic.getMessages(String.class)
		     .doOnNext(System.out::println)
		     .subscribe();
		
		sleep(60000);
	}
	
	@Test
	public void publisher() {
		RTopicReactive topic = this.redissonReactiveClient.getTopic("slackhome", StringCodec.INSTANCE);
		
		topic.publish("asdasdasd").subscribe();
		
		sleep(60000);
	}
	
	@Test
	public void dummy() {
		Mono<Void> mono = Flux.just(1,2,3,4,5)
		    .doFinally(signal -> {
		    	System.out.println(signal);
		    	System.out.println("doFinally");
		    })
		    .doOnNext(System.out::println)
		    .then(Mono.just("asdad"))
		    .doOnNext(s -> System.out.println(s.toUpperCase()))
		    .doFinally(signal -> {
		    	System.out.println(signal);
		    	System.out.println("doFinally2");
		    })
		    .then();
		
		StepVerifier.create(mono)
		            .verifyComplete();
	}

}
