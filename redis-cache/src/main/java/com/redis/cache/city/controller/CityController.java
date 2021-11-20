package com.redis.cache.city.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redis.cache.city.dto.City;
import com.redis.cache.city.service.CityService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("city")
public class CityController {
	
	@Autowired
	private CityService cityService;
	
	@GetMapping("{zipcode}")
	public Mono<City> getCity(@PathVariable String zipcode) {
		return this.cityService.getCity(zipcode);
	}

}
