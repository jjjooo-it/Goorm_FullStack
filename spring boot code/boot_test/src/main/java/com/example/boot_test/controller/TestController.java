package com.example.boot_test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {
	@GetMapping("/protected")
	public String protect() {
		return "로그인 해야 접근 가능";
	}
	@GetMapping("/public")
	public String open() {
		return "아무나 접근 가능";
	}
}
