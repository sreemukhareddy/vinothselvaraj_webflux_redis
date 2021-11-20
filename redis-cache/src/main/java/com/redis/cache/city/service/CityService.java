package com.redis.cache.city.service;

import java.util.function.Function;
import java.util.stream.Collectors;

import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.redis.cache.city.dto.City;

import reactor.core.publisher.Mono;

@Service
public class CityService {
	
	// START == TIME_TO_LIVE FOR KEYS AND VALUES
	
	/*
	private RMapCacheReactive<String, City> cityMap;
	
	@Autowired
	private CityClient cityClient;
	
	public CityService(RedissonReactiveClient client) {
		this.cityMap = client.getMapCache("city", new TypedJsonJacksonCodec(String.class, City.class));
	}
	
	public Mono<City> getCity(String zipCode) {
		return this.cityMap.get(zipCode)
				   .switchIfEmpty(cityClient.getCity(zipCode)
						   			.flatMap(c -> this.cityMap.fastPut(zipCode, c, 100, TimeUnit.SECONDS).thenReturn(c))
						   );
	}
	*/
	
	// END == TIME_TO_LIVE FOR KEYS AND VALUES
	
	
	private RMapReactive<String, City> cityMap;
	
	@Autowired
	private CityClient cityClient;
	
	public CityService(RedissonReactiveClient client) {
		this.cityMap = client.getMap("city", new TypedJsonJacksonCodec(String.class, City.class));
	}
	
	public Mono<City> getCity(String zipCode) {
		return this.cityMap.get(zipCode)				           
				           .onErrorResume(ex -> cityClient.getCity(zipCode))
				           ;
	}
	
	//@Scheduled(fixedRate = 15000)
	public void updateCity() {
		this.cityClient.getAllCities()
						.doFirst(() -> {
							System.out.println("STARTED..!");
						})
						.doFinally(signal -> {
		            	   System.out.println(signal);
		            	   System.out.println("DONE READING THE VALUES FROM WEBCLIENT");
		               })
		               .collectList()
		               .map(listOfCities -> listOfCities.stream().collect(Collectors.toMap(City::getZip, Function.identity())))
		               .flatMap(this.cityMap::putAll)
		               .doFinally(signal -> {
		            	   System.out.println(signal);
		            	   System.out.println("DONE..!");
		               })
		               .subscribe();
		
		//.map(listOfCities -> listOfCities.stream().collect(Collectors.toMap(City::getZip, v->v)))
	}
	
}
