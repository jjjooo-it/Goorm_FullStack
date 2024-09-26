package com.example.boot_test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.boot_test.model.User;

public interface UserRepository extends
	JpaRepository<User, Long>{
    List<User> findTop5ByEmailContainingOrderByEmailDesc(String keyword);
}
