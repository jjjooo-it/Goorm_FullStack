package com.example.boot_security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.boot_security.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	public Optional<Member> findByUsername(String username);
}
