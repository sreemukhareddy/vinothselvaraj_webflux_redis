package com.redis.cache.fib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redis.cache.fib.service.FibService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("fib")
public class FibController {
	
	@Autowired
	private FibService fibService;
	
	@GetMapping("{index}/{name}")
	public Mono<Integer> getFib(@PathVariable Integer index, @PathVariable String name) {
		return Mono.fromSupplier(() -> this.fibService.getfib(index, name));
	}
	
	@GetMapping("{index}/clear")
	public Mono<Void> clearCache(@PathVariable Integer index) {
		return Mono.fromRunnable(() -> fibService.clearCache(index));
	}
	

}
