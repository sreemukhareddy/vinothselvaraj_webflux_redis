package com.vinsguru.redis.config;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.redisson.api.RMapCacheReactive;
import org.redisson.codec.TypedJsonJacksonCodec;

import com.vinsguru.redis.dto.Student;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec07MapCacheTest extends BaseTest{
	
	@Test
	public void mapTest3() {
		TypedJsonJacksonCodec codec = new TypedJsonJacksonCodec(Integer.class, Student.class);
		RMapCacheReactive<Integer, Student> mapCache = this.redissonReactiveClient.getMapCache("users:cache", codec);
		
		Mono<Void> mono = Flux.range(1, 3)
		    .flatMap(i -> {
		    	Student student = Student.builder().name(" " + i).age(i).city(i + "").pass(true).build();
		    	return mapCache.put(i, student, 5, TimeUnit.SECONDS);
		    })
		    .then();
		
		StepVerifier.create(mono)
		            .verifyComplete();
		
		sleep(3000);
		
		Mono<Void> do1 = mapCache.get(1)
		        .doOnNext(System.out::println).then();;
		
		Mono<Void> do2 = mapCache.get(2)
        .doOnNext(System.out::println).then();;
		
		Mono<Void> do3 = mapCache.get(3)
        .doOnNext(System.out::println).then();;
		
		StepVerifier.create(do1.concatWith(do2).concatWith(do3))
		           .verifyComplete();
		
sleep(3000);
		
		Mono<Void> do4 = mapCache.get(1)
		        .doOnNext(System.out::println).then();
		
		Mono<Void> do5 = mapCache.get(2)
        .doOnNext(System.out::println).then();
		
		Mono<Void> do6 = mapCache.get(3)
        .doOnNext(System.out::println).then();
		
		StepVerifier.create(do4.concatWith(do5).concatWith(do6))
		            .verifyComplete();
	}

}
