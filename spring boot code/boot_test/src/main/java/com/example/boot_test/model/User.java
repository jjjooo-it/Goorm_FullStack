package com.example.boot_test.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(
			strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false, unique = true)
	private String email;
	
	public User() {}
	
	public User(String name, String email) {
		this.name = name;
		this.email = email;
		// this.id = 1L; // 잘못된 메서드 실행
	}
}
