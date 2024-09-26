package com.example.boot_sns.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Entity // JPA가 인식할 수 있게...
@Data // Getter, Setter, 생성자...
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private LocalDateTime creatdAt;
	
	@PrePersist // save이 되기 전에
	protected void onCreate() {
		creatdAt = LocalDateTime.now(); // 현재 시간을
	}
	
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<Post> posts = new ArrayList<>();
	
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();
	
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<Like> likes = new ArrayList<>();
	
	@OneToMany(mappedBy = "follower", cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<Follow> following = new ArrayList<>();
	
	@OneToMany(mappedBy = "following", cascade = CascadeType.ALL,
			orphanRemoval = true)	
	private List<Follow> followers = new ArrayList<>();
}
