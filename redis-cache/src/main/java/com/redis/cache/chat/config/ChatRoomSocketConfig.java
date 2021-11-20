package com.redis.cache.chat.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;

import com.redis.cache.chat.service.ChatRoomService;

@Configuration
public class ChatRoomSocketConfig {
	
	@Autowired
	private ChatRoomService chatRoomService;
	
	@Bean
	public HandlerMapping handlerMapping() {
		Map<String, ChatRoomService> map = Map.of("/chat", chatRoomService);
		return new SimpleUrlHandlerMapping(map, -1);
	}

}
