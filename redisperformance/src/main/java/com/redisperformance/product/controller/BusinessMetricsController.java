package com.redisperformance.product.controller;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redisperformance.product.service.BusinessMetricsService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("product/metrics")
public class BusinessMetricsController {

	@Autowired
	private BusinessMetricsService businessMetricsService;
	
	@GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Flux<Map<Integer,Double>> getMetrics() {
		return this.businessMetricsService.topThreeProducts()
				   .repeatWhen(l -> Flux.interval(Duration.ofSeconds(3)));
	}
}
