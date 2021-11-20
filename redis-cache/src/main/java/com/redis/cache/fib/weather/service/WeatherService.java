package com.redis.cache.fib.weather.service;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
	
	@Autowired
	private ExternalServiceclient client;
	
	@Cacheable("weather")
	public int getInfo(int zip) {
		return 0;
	}
	
	//@Scheduled(fixedRate = 10000)
	public void update() {
		System.out.println("Updating the weather for zip");
		IntStream.range(1, 6)
		         .forEach(this.client::getWeatherInfo)
		         ;
	}

}
