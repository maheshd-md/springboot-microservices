package com.example.demo;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BookDeserializer implements Deserializer<Book> {

	@Override
	public Book deserialize(String topic, byte[] data) {
		ObjectMapper objectMapper = new ObjectMapper();
		Book book = null;
		try {
			book = objectMapper.readValue(data, Book.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return book;
	}

}
