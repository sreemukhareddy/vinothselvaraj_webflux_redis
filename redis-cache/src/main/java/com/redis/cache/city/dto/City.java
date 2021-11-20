package com.redis.cache.city.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class City {
	
	private String zip;
	private String city;
	private String stateName;
	private int temperature;
	
	

}
