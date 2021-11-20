
package com.redisperformance.product.util;

import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.redisperformance.product.entity.Product;
import com.redisperformance.product.repository.ProductRespository;

import reactor.core.publisher.Mono;

//@Component
public class ProductCacheTemplate extends CacheTemplate<Integer, Product> {

	private RMapReactive<Integer, Product> map;

	@Autowired
	private ProductRespository productRepository;

	public ProductCacheTemplate(RedissonReactiveClient client) {
		this.map = client.getMap("product", new TypedJsonJacksonCodec(Integer.class, Product.class));
	}

	@Override
	protected Mono<Product> getFromSource(Integer key) {
		return this.productRepository.findById(key);
	}

	@Override
	protected Mono<Product> getFromCache(Integer key) {
		return this.map.get(key);
	}

	@Override
	protected Mono<Product> updateSource(Integer key, Product entity) {
		return this.productRepository.findById(key)
				.map(savedProduct -> {
						entity.setId(savedProduct.getId());
						return entity;
				})
				.flatMap(this.productRepository::save);
	}

	@Override
	protected Mono<Product> updateCache(Integer key, Product entity) {
		return this.map.fastPut(key, entity).thenReturn(entity);
	}

	@Override
	protected Mono<Void> deleteFromSource(Integer key) {
		return this.productRepository.deleteById(key);
	}

	@Override
	protected Mono<Void> deleteFromCache(Integer key) {
		return this.map.fastRemove(key).then();
	}

}
