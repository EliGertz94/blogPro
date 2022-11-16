package com.example.blogPro;

import com.example.blogPro.service.CommentService;
import com.example.blogPro.service.impl.CommentServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogProApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogProApplication.class, args);
	}

}
