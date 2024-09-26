package com.example.boot_test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class AssertionTest {
	
	// 문자열
	@Test
	void testString() {
		// 문자열
		// String str = null;
		// String str = "Bye, world";
		// String str = "Hello, world";
		// String str = "Hello, world!";
		String str = "Hello, AssertJ!";
		
		// 문자열에 대해서 검증
		assertThat(str)
			.isNotNull() // 문자열이 null이면 안 됨
			.startsWith("Hello")
			// 문자열이 Hello로 시작해야함
			.endsWith("!")
			// 문자열이 !로 끝나는지 검증
			.contains("AssertJ")
			.hasSize(15) // 길이가 15임을 검증
		;
	}
	
	@Test
	void testInteger() {
//		Integer num = null;
//		Integer num = 39;
//		Integer num = 51;
//		Integer num = 49;
		Integer num = 42;
		
		assertThat(num)
			.isNotNull()
			.isGreaterThan(40) // 초과 -> 40보다 커야 통과
			.isLessThan(50) // 50 미만
			// .is...ThanOrEqualTo -> 이상, 이하
			.isEqualTo(42);
	}
	
	@Test
	void testList() {
		List<String> list = Arrays.asList(
				"사과", "바나나", "체리");
		
		assertThat(list)
			.isNotNull()
			.hasSize(3) // 리스트의 크기가 3인지
			.contains("바나나") // 바나나를 포함하고 있는지
			.doesNotContain("포도") // 포함하지 않는지
			.containsExactly("사과", "바나나", "체리");
			// 정확하게 이 순서대로 포함하는지를 검사
	}
}
