package com.roofiahmad.springstoreapp;

import com.roofiahmad.springstoreapp.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringStoreAppApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringStoreAppApplication.class, args);
		UserService userService = (UserService) context.getBean("userService");
		userService.deleteProduct();
//		userService.deleteRelated();
	}


}