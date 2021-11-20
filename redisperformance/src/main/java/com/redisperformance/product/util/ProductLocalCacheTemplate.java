
package com.redisperformance.product.util;

import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.LocalCachedMapOptions.ReconnectionStrategy;
import org.redisson.api.LocalCachedMapOptions.SyncStrategy;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RedissonClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.redisperformance.product.entity.Product;
import com.redisperformance.product.repository.ProductRespository;

import reactor.core.publisher.Mono;

@Component
public class ProductLocalCacheTemplate extends CacheTemplate<Integer, Product> {

	private RLocalCachedMap<Integer, Product> map;

	@Autowired
	private ProductRespository productRepository;

	public ProductLocalCacheTemplate(RedissonClient client) {
		LocalCachedMapOptions<Integer,Product> mapOptions = LocalCachedMapOptions.<Integer, Product>defaults()
		                     .syncStrategy(SyncStrategy.UPDATE)
		                     .reconnectionStrategy(ReconnectionStrategy.CLEAR);
		                     
		this.map = client.getLocalCachedMap("product", new TypedJsonJacksonCodec(Integer.class, Product.class), mapOptions);
	}

	@Override
	protected Mono<Product> getFromSource(Integer key) {
		return this.productRepository.findById(key);
	}

	@Override
	protected Mono<Product> getFromCache(Integer key) {
		return Mono.justOrEmpty(this.map.get(key));
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
		return Mono.create(monoSink -> {
			this.map.fastPutAsync(key, entity)
			        .thenAccept(b -> {
			        	monoSink.success(entity);
			        })
			        .exceptionally(ex -> {
			        	monoSink.error(ex);
			        	return null;
			        });
		});
	}

	@Override
	protected Mono<Void> deleteFromSource(Integer key) {
		return this.productRepository.deleteById(key);
	}

	@Override
	protected Mono<Void> deleteFromCache(Integer key) {
		return Mono.create(monoSink -> {
			this.map.fastRemoveAsync(key)
			        .thenAccept(b -> {
			        	monoSink.success();
			        })
			        .exceptionally(ex -> {
			        	monoSink.error(ex);
			        	return null;
			        });
		});
	}

}
