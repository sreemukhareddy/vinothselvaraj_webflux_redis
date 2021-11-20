package com.vinsguru.redis.config;

import java.time.Duration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RedissonClient;
import org.redisson.codec.TypedJsonJacksonCodec;

import com.vinsguru.redis.dto.Student;

import reactor.core.publisher.Flux;

public class Lec08LocalCachedMapTest extends BaseTest{
	
	private RLocalCachedMap<Integer, Student> studentsMap;
	
	@BeforeAll
	public void init() {
		ReddisonConfig config = new ReddisonConfig();
		RedissonClient client = config.getRedissonClient();
		
		LocalCachedMapOptions<Integer, Student> mapOptions = LocalCachedMapOptions.<Integer, Student>defaults()
		                     .syncStrategy(LocalCachedMapOptions.SyncStrategy.UPDATE)
		                     .reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.NONE);
		        
		this.studentsMap = client.getLocalCachedMap("student", new TypedJsonJacksonCodec(Integer.class, Student.class), mapOptions);
	}
	
	@Test
	public void appServer1() {
		Student student1 = Student.builder().name("name_1").age(25).city("city_1").pass(true).build();
		Student student2 = Student.builder().name("name_2").age(55).city("city_2").pass(false).build();
		
		this.studentsMap.put(1, student1);
		this.studentsMap.put(2, student2);
		
		Flux.interval(Duration.ofSeconds(1))
		    .doOnNext(i ->System.out.println( i + " ==> "  + this.studentsMap.get(1)) ).subscribe();
		
		sleep(60000);
	}
	
	@Test
	public void appServer2() {
		Student student1 = Student.builder().name("name_1-updated").age(25).city("city_1").pass(true).build();
		
		this.studentsMap.put(1, student1);
		
		Flux.interval(Duration.ofSeconds(1))
		    .doOnNext(i ->System.out.println( i + " ==> "  + this.studentsMap.get(1)) ).subscribe();
		
		sleep(60000);
	}

}
