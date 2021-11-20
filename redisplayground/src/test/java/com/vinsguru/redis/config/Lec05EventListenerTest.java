package com.vinsguru.redis.config;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.redisson.api.DeletedObjectListener;
import org.redisson.api.ExpiredObjectListener;
import org.redisson.api.RBucketReactive;
import org.redisson.client.codec.StringCodec;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec05EventListenerTest extends BaseTest{
	
	// config set notify-keyspace-events AKE
	@Test
	public void expireEventListenerTest() {
		RBucketReactive<String> bucket = this.redissonReactiveClient.getBucket("user:1:name", StringCodec.INSTANCE);
		Mono<Void> set = bucket.set("first_name", 5, TimeUnit.SECONDS);
		Mono<Void> get = bucket.get()
		      .doOnNext(System.out::println)
		      .then();
		
		Mono<Void> addListener = bucket.addListener( new ExpiredObjectListener() {
			@Override
			public void onExpired(String key) {
				System.out.println("Expired key is " + key);
			}
		}).then();
		

		StepVerifier.create(set.concatWith(get).concatWith(addListener))
		            .verifyComplete();
		sleep(6500);
	}
	
	@Test
	public void deleteEventListenerTest() {
		RBucketReactive<String> bucket = this.redissonReactiveClient.getBucket("user:1:name", StringCodec.INSTANCE);
		Mono<Void> set = bucket.set("first_name");
		Mono<Void> get = bucket.get()
		      .doOnNext(System.out::println)
		      .then();
		
		Mono<Void> addListener = bucket.addListener( new DeletedObjectListener() {
			@Override
			public void onDeleted(String key) {
				System.out.println("Deleted key is " + key);
			}
		}).then();
		

		StepVerifier.create(set.concatWith(get).concatWith(addListener))
		            .verifyComplete();
		sleep(65000);
	}

}
