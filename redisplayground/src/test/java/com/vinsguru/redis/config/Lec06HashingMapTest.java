package com.vinsguru.redis.config;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.redisson.api.RMapReactive;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.TypedJsonJacksonCodec;

import com.vinsguru.redis.dto.Student;
import com.vinsguru.redis.dto.Student.StudentBuilder;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec06HashingMapTest extends BaseTest{
	
	@Test
	public void mapTest() {
		RMapReactive<String, String> user1 = this.redissonReactiveClient.getMap("user:1", StringCodec.INSTANCE);
		
		Mono<String> name = user1.put("name", "allu arjun");
		Mono<String> age = user1.put("age", "20");
		Mono<String> city = user1.put("city", "hyderabad");
		
		StepVerifier.create(name.concatWith(age).concatWith(city))
		            .verifyComplete();
	}
	
	@Test
	public void mapTest2() {
		RMapReactive<String, String> user1 = this.redissonReactiveClient.getMap("user:1", StringCodec.INSTANCE);
		
		Map<String, String> map = Map.of(
				"name", "allu arjun2",
				"age", "202",
				"city", "hyderabad2"
				);
		Mono<Void> mapMono = user1.putAll(map);
		StepVerifier.create(mapMono)
		            .verifyComplete();
	}
	
	@Test
	public void mapTest3() {
		TypedJsonJacksonCodec codec = new TypedJsonJacksonCodec(Integer.class, Student.class);
		RMapReactive<Integer, Student> user1 = this.redissonReactiveClient.getMap("users", codec);
		
		Mono<Void> mono = Flux.range(1, 3)
		    .flatMap(i -> {
		    	Student student = Student.builder().name(" " + i).age(i).city(i + "").pass(true).build();
		    	return user1.put(i, student);
		    })
		    .then();
		
		Mono<Void> get = user1.get(1).doOnNext(System.out::println).then();
		
		StepVerifier.create(mono.concatWith(get))
		            .verifyComplete();
	}

}
