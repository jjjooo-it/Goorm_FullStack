package com.example.boot_test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.boot_test.model.User;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@PostMapping
	public ResponseEntity<User> createUser(
			@RequestBody User user
	) {
		// user.setId(2L);
		 user.setId(1L);
		// return ResponseEntity.status(HttpStatus.CONFLICT)
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(user);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUser(
			@PathVariable Long id) {
//		User user = new User("최자바", "choi@java.com");
		User user = new User("박자바", "park@java.com");
//		user.setId(id + 100);
		user.setId(id);
//		return ResponseEntity
//				.status(HttpStatus.BAD_REQUEST).body(user)
		return ResponseEntity.ok(user);
	}
}
