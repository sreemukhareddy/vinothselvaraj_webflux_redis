package com.redis.cache.fib.weather.service;

import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import io.netty.util.internal.ThreadLocalRandom;

@Service
public class ExternalServiceclient {
	
	@CachePut(value = "weather", key = "#zip")
	public int getWeatherInfo(int zip) {
		int nextInt = ThreadLocalRandom.current().nextInt(60, 100);
		System.out.println("zip ==> " + zip + ", temp ==> " +nextInt);
		return nextInt;
	}

}
