package com.example.boot_sns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.boot_sns.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
