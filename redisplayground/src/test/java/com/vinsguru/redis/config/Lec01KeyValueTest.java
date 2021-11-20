package com.vinsguru.redis.config;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.redisson.api.RBucketReactive;
import org.redisson.client.codec.StringCodec;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec01KeyValueTest extends BaseTest{
	
	@Test
	public void keyValueAccessTest() {
		RBucketReactive<String> bucket = this.redissonReactiveClient.getBucket("user:1:name", StringCodec.INSTANCE);
		Mono<Void> set = bucket.set("first_name");
		Mono<Void> get = bucket.get()
		      .doOnNext(System.out::println)
		      .then();
		
		StepVerifier.create(set.concatWith(get))
		            .verifyComplete();
	}
	
	@Test
	public void keyValueAccessExpiryTest() {
		RBucketReactive<String> bucket = this.redissonReactiveClient.getBucket("user:1:name", StringCodec.INSTANCE);
		Mono<Void> set = bucket.set("first_name", 15, TimeUnit.SECONDS);
		Mono<Void> get = bucket.get()
		      .doOnNext(System.out::println)
		      .then();
		
		StepVerifier.create(set.concatWith(get))
		            .verifyComplete();
	}
	
	@Test
	public void extendExpiryTest() {
		RBucketReactive<String> bucket = this.redissonReactiveClient.getBucket("user:1:name", StringCodec.INSTANCE);
		Mono<Void> set = bucket.set("first_name", 15, TimeUnit.SECONDS);
		Mono<Void> get = bucket.get()
		      .doOnNext(System.out::println)
		      .then();
		
		StepVerifier.create(set.concatWith(get))
		            .verifyComplete();
		
		sleep(8);
		// to expand the ttl == time-to-live
		Mono<Boolean> expire = bucket.expire(60, TimeUnit.SECONDS);
		StepVerifier.create(expire)
		            .expectNext(true)
		            .verifyComplete();
		
		Mono<Void> ttlMono = bucket.remainTimeToLive().doOnNext(System.out::println).then();
		
		StepVerifier.create(ttlMono)
		            .verifyComplete();
		
	}

}
