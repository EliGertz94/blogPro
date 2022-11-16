package com.example.blogPro;

import com.example.blogPro.service.CommentService;
import com.example.blogPro.service.impl.CommentServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogProApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogProApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
