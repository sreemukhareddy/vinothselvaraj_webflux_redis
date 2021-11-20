package com.redisperformance.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redisperformance.product.entity.Product;
import com.redisperformance.product.exception.NoProductFoundException;
import com.redisperformance.product.repository.ProductRespository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceV1 {

	@Autowired
	private ProductRespository productRespository;
	
	public Mono<Product> getProduct(int id) {
		return productRespository.findById(id);
	}
	
	public Mono<Product> updateProduct(int proId, Mono<Product> productMono) {
		return productRespository.findById(proId)
				.flatMap(existingProduct -> productMono.map(currentProduct -> {
															existingProduct.setDescription(currentProduct.getDescription());
															existingProduct.setPrice(currentProduct.getPrice());
															return existingProduct;
				}))
				.flatMap(productRespository::save)
				.switchIfEmpty(Mono.error(new NoProductFoundException(proId)));
				//.switchIfEmpty(productMono.flatMap(productRespository::save));
	}

}
