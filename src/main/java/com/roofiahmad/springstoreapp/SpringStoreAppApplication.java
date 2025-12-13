package com.roofiahmad.springstoreapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringStoreAppApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringStoreAppApplication.class, args);
		var orderService = context.getBean(OrderService.class);
		orderService.placeOrder();
	}

}