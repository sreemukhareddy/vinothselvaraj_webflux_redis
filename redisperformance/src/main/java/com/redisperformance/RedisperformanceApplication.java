package com.redisperformance;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.util.StreamUtils;

import com.redisperformance.product.entity.Product;
import com.redisperformance.product.repository.ProductRespository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class RedisperformanceApplication implements CommandLineRunner{
	
	@Autowired
	private ProductRespository productRespository;
	
	@Autowired
	private R2dbcEntityTemplate r2dbcEntityTemplate;
	
	@Value("classpath:schema.sql")
	private Resource resource;

	public static void main(String[] args) {
		SpringApplication.run(RedisperformanceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String sql = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
		
		Mono<Void> mono = Flux.range(1, 10)
		    .map( i -> Product.builder().description("destination_"+i).price(i * 100).build())
		    .collectList()
		    .flatMapMany(list -> productRespository.saveAll(list))
		    .then();
		
		r2dbcEntityTemplate.getDatabaseClient()
						   .sql(sql)
						   .then()
						   .then(mono)
						   .subscribe();
						   
		    
	}

}
