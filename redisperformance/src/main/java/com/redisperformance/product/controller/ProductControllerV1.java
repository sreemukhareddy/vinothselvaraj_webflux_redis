package com.redisperformance.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redisperformance.product.entity.Product;
import com.redisperformance.product.service.ProductServiceV1;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product/v1")
public class ProductControllerV1 {
	
	@Autowired
	private ProductServiceV1 productService;
	
	@GetMapping("{id}")
	public Mono<Product> getProduct(@PathVariable Integer id) {
		return productService.getProduct(id);
	}
	
	@PutMapping("{id}")
	public Mono<Product> updateProduct(@PathVariable Integer id, @RequestBody Mono<Product> productMono) {
		return productService.updateProduct(id, productMono);
	}

}
