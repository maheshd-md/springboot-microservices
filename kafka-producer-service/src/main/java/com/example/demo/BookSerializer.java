package com.example.demo;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BookSerializer implements Serializer<Book> {

	@Override
	public byte[] serialize(String topic, Book data) {
		byte[] byteArr = null;
		
		if(null == data) {
			return null;
		}
		try {
			byteArr = new ObjectMapper().writeValueAsBytes(data);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return byteArr;
	}

}
