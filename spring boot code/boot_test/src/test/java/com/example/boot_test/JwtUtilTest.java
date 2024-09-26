package com.example.boot_test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Key;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.example.boot_test.util.JwtUtil;

import io.jsonwebtoken.security.Keys;

@SpringBootTest
@AutoConfigureMockMvc
public class JwtUtilTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private JwtUtil jwtUtil;
	
	private final String SECRET_KEY = "yourSecretKeyHereMustBeAtLeast32BytesLong";
	private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	
	@Test
	void test1() {
		String username = "testuser";
		String token = jwtUtil
				.generateToken(username);
		String extractedUsername = jwtUtil
				.extractUsername(token);
		
		assertThat(extractedUsername)
			.isEqualTo(username);
	}
	
	@Test
	void test2() throws Exception {
		String token = jwtUtil.generateToken("testuser");
		mockMvc.perform(get("/api/protected")
				.header("Authorization",
						"Bearer " + token)
				).andExpect(status().isOk());
	}
	
	@Test
	void test3() throws Exception {
		// String token = jwtUtil.generateToken("testuser");
		mockMvc.perform(get("/api/protected")
//				.header("Authorization",
//						"Bearer " + token)
				).andExpect(status().isForbidden());
	}
	
	@Test
	void test4() throws Exception {
		// String token = jwtUtil.generateToken("testuser");
		mockMvc.perform(get("/api/protected")
				.header("Authorization",
						"Bearer " + "잘못된토큰")
				).andExpect(status().isForbidden());
	}
}
