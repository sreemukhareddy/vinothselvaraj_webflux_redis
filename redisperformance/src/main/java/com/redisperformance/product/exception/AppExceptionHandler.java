package com.redisperformance.product.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {
	
	@SuppressWarnings("static-access")
	@ExceptionHandler(NoProductFoundException.class)
	public ResponseEntity<String> handleNoProductFoundException(NoProductFoundException ex) {
		return ResponseEntity.badRequest().body("No Product Found For The Given Id " + ex.getId())	;			             
	}

}
