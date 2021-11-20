package com.vinsguru.redis.config;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.redisson.api.RBucketReactive;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.TypedJsonJacksonCodec;

import com.vinsguru.redis.dto.Student;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec02KeyValueObjectTest extends BaseTest{

	
	@Test
	public void keyvalueObjectTest() {
		Student student = Student.builder().name("name").age(100).city("city").pass(true).build();
		RBucketReactive<Student> bucket = this.redissonReactiveClient.getBucket("student:1", JsonJacksonCodec.INSTANCE);
		Mono<Void> set = bucket.set(student);
		Mono<Void> get = bucket.get()
		      .doOnNext(System.out::println).then();
		StepVerifier.create(set.concatWith(get)).verifyComplete();
	}
	
	// prefer to use this one than the above. it removes the @class info in redis cli
	@Test
	public void keyvalueObjectTest2() {
		Student student = Student.builder().name("name").age(100).city("city").pass(true)
				.marks(Arrays.asList(1,2,3,4,5,6))     
				.build();
		RBucketReactive<Student> bucket = this.redissonReactiveClient.getBucket("student:1", new TypedJsonJacksonCodec(Student.class));
		Mono<Void> set = bucket.set(student);
		Mono<Void> get = bucket.get()
		      .doOnNext(System.out::println).then();
		StepVerifier.create(set.concatWith(get)).verifyComplete();
	}
}
