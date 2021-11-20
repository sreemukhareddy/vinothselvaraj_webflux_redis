package com.redisperformance.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redisperformance.product.entity.Product;
import com.redisperformance.product.util.CacheTemplate;

import reactor.core.publisher.Mono;

@Service
public class ProductServiceV2 {
	
	@Autowired
	private CacheTemplate<Integer, Product> cache;
	
	@Autowired
	private ProductVisitService productVisitService;
	
	public Mono<Product> getProduct(int id) {
		return cache.get(id).doFirst(() -> this.productVisitService.addVisit(id));
	}
	
	public Mono<Product> updateProduct(int proId, Product product) {
		return cache.update(proId, product);
				          
	}
	
	public Mono<Void> deleteProduct(int id) {
		return this.cache.delete(id);
	}
	
	
}
