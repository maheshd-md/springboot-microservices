package com.example.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookResource {
	
	@Value("${server.port}")
	int port;
	
	public static List<Book> bookList = new ArrayList<>();
	
	@GetMapping("/getbooks/{authorName}")
	public List<Book> getBooksByAuthorName(@PathVariable String authorName) {
		System.out.println("DB-SERVICE Port: " +port);
		return bookList.stream()
				.filter(book -> authorName.equals(book.getAuthor()))
				.collect(Collectors.toList());
	}
	
	@PostMapping("/savebook")
	public Book saveBook(@RequestBody Book book) {
		long maxId = 0;
		if(!CollectionUtils.isEmpty(bookList)) {
			maxId = Collections.max(bookList, Comparator.comparing(book1 -> book1.getId())).getId();
		}
		book.setId(1 + maxId);
		bookList.add(book);
		return book;
	}
}
