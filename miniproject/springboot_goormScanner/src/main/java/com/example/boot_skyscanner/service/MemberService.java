package com.example.boot_skyscanner.service;

import com.example.boot_skyscanner.model.FlightBook;
import com.example.boot_skyscanner.model.Member;
import com.example.boot_skyscanner.repository.FlightBookRepository;
import com.example.boot_skyscanner.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final FlightBookRepository flightBookRepository;


    @Transactional
    public void join(String email, String password, String enName, String koName, String gender, String mobile, String role,String username) {
        if (memberRepository.existsByUsername(username)) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
        System.out.println("Starting join process for: " + email);


        Member member = new Member();
        member.setUsername(username);
        member.setEmail(email);
        member.setPassword(passwordEncoder.encode(password));
        member.setEnName(enName);
        member.setKoName(koName);
        member.setGender(gender);
        member.setMobile(mobile);
//        member.setRole(role);
        member.setRole("ROLE_USER");
        System.out.println("Saving member: " + member);

        memberRepository.save(member);
        System.out.println("Successfully joined member: " + member);
    }
    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
    }
}


