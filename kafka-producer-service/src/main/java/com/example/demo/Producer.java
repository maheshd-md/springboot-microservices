package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Producer {

	@Autowired
	private KafkaTemplate<String, Book> kafkaTemplate;

	@PostMapping(value = "/publish/{topic}")
	public boolean sendMessage(@PathVariable String topic, @RequestBody Book book) {

		ListenableFuture<SendResult<String, Book>> future = kafkaTemplate.send(topic, book);
		future.addCallback(new ListenableFutureCallback<SendResult<String, Book>>() {

			@Override
			public void onSuccess(SendResult<String, Book> result) {
				System.out.println("Sent message=[" + book + "] with offset=["
						+ result.getRecordMetadata().offset() + "]");
			}

			@Override
			public void onFailure(Throwable ex) {
				System.out.println("Unable to send message=[" + book + "] due to : " + ex.getMessage());
			}
		});

		return true;
	}

}
