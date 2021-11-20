package com.redisperformance.product.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.redisperformance.product.entity.Product;

@Repository
public interface ProductRespository extends ReactiveCrudRepository<Product, Integer>{

}
