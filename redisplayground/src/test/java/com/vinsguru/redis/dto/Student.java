package com.vinsguru.redis.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student implements Serializable{

	private static final long serialVersionUID = 1L;

	private String name;
	private int age;
	private String city;
	private boolean pass;
	private List<Integer> marks;
}
