package com.redisperformance.product.exception;

import lombok.Data;

@Data
public class NoProductFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private int id;

	public NoProductFoundException(int id) {
		this.id = id;
	}
	
}
