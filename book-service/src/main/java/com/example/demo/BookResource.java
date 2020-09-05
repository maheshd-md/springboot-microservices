package com.example.demo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class BookResource {

	@Value("${server.port}")
	int port;
	
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping(value = "/getbooks/{authorName}", produces = "application/json")
	@HystrixCommand(fallbackMethod = "getDefaultBookList")
	public List<Book> getBooksByAuthorName(@PathVariable String authorName) {
		System.out.println("DB-SERVICE Port: " +port);
		ResponseEntity<List<Book>> response = null;
		
		try {
			response = restTemplate.exchange(
					new URI("http://db-service/getbooks/" + authorName), HttpMethod.GET, null,
					new ParameterizedTypeReference<List<Book>>() {
					});
			
		} catch (RestClientException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response.getBody();
	}

	@PostMapping(value = "/savebook", produces = "application/json", consumes = "application/json")
	public Book saveBook(@RequestBody Book book) {

		ResponseEntity<Book> response = null;
		try {
			response = restTemplate.postForEntity(
					new URI("http://db-service/savebook"), 
					book, 
					Book.class);
		} catch (RestClientException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.getBody();
	}
	
	private List<Book> getDefaultBookList(String authorName) {
		Book book = new Book();
		book.setAuthor(authorName);
		book.setBookName("Life");
		book.setId(0l);
		return Collections.singletonList(book);
	}
}