package com.redisperformance.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redisperformance.product.entity.Product;
import com.redisperformance.product.service.ProductServiceV2;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product/v2")
public class ProductControllerV2 {
	
	@Autowired
	private ProductServiceV2 productService;
	
	@GetMapping("{id}")
	public Mono<Product> getProduct(@PathVariable Integer id) {
		return productService.getProduct(id);
	}
	
	@PutMapping("{id}")
	public Mono<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {
		return productService.updateProduct(id, product);
	}

	@DeleteMapping("{id}")
	public Mono<Void> deleteProduct(@PathVariable Integer id) {
		return productService.deleteProduct(id);
	}
}
