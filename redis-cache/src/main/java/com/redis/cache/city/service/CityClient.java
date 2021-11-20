package com.redis.cache.city.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.redis.cache.city.dto.City;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CityClient {

	private WebClient webClient;
	
	public CityClient(@Value("${city.service.url}") String url) {
		this.webClient = WebClient.builder().baseUrl(url).build();
	}
	
	public Mono<City> getCity(final String zipcode) {
		return webClient.get()
				        .uri("{zipcode}", zipcode)
				        .retrieve()
				        .bodyToMono(City.class);
	}
	
	public Flux<City> getAllCities(){
		return webClient.get()
				        .retrieve()
				        .bodyToFlux(City.class);
	}
}
