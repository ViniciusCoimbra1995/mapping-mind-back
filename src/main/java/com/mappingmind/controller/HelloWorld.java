package com.mappingmind.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class HelloWorld {
	
	@GetMapping(value = "/hello")
	public String helloWorld() {
		
		return "Hey, Bem-vindo!";
	}
	

}
