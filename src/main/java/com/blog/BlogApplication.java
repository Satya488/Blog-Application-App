package com.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {

		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean  // @Bean will generate object by this method with help of new keyword and return that object address
    public ModelMapper modelMapper(){
		return  new ModelMapper();
	}
}
