package com.example.demo;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class Consumer {

	@Autowired
	private RestTemplate restTemplate;
	
	@KafkaListener(topics = "books", groupId = "demo")
	public void consume(@Payload Book book) {
		try {
			restTemplate.postForEntity(
					new URI("http://book-service/savebook"), 
					book, 
					Book.class);
		} catch (RestClientException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}