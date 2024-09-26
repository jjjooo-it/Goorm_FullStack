package com.example.boot_test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void testCreateUser() throws Exception {
		String userJson = "{\"name\":\"김자바\","
				+ "\"email\":\"kim@java.com\"}";
		mockMvc.perform(post("/api/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").exists())
			.andExpect(jsonPath("$.id").value(1));
	}
	
	@Test
	void testGetUser() throws Exception {
		mockMvc.perform(get("/api/users/1"))
			.andExpect(status().isOk())
			.andExpect(
					jsonPath("$.name").value("박자바"))
			.andExpect(
					jsonPath("$.id").value(1));
	}
}









