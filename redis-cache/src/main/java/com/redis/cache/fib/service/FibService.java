package com.redis.cache.fib.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FibService {
	
	// GET METHOD WILL HAVE THIS ANNOTATION
	@Cacheable(value = "math:fib" , key = "#index")
	public int getfib(int index, String name) {
		System.out.println("Calculating fib for " + index + ", name " + name);
		return fib(index);
	}
	
	// PUT, POST, DELETE, PATCH will have this annotation
	@CacheEvict(value = "math:fib" , key = "#index")
	public void clearCache(int index) {
		System.out.println("Cleared cache for the index " + index);
	}
	
	@Scheduled(fixedRate = 10000)
	@CacheEvict(value = "math:fib", allEntries = true)
	public void clearAllMathFibCache() {
		System.out.println("Clearing all the math:fib cache");
	}
	
	private int fib(int index) {
		if(index < 2) {
			return index;
		}
		return fib(index-1) + fib(index-2);
	}

}
