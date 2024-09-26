package com.example.boot_test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalculatorTest {
	private Calculator calculator;
	
	@BeforeEach // 각 테스트 메서드 실행 전에...
	void setUp() {
		calculator = new Calculator();
	}
	
	@Test
	@DisplayName("1 + 1 = 2")
	void testAdd() {
		assertEquals(2, calculator.add(1, 1), "1 + 1 은 2임");
	}
	
	@Test
	@DisplayName("2 + 2 = 4")
	void testAdd2() {
		assertEquals(4, calculator.add(2, 2), "1 + 1 은 2임");
	}
	
	@Test
	@DisplayName("4 / 2 = 2")
	void testDivide() {
		assertEquals(2, calculator.divide(4, 2), "4 / 2 는 2임");
	}
	
	@Test
	@DisplayName("1 / 0 = ArithmeticException")
	void testDivide2() {
		assertThrows(
				ArithmeticException.class,
				() -> calculator.divide(1, 0),
				"1 / 0 은 에러 발생");
	}
	
	@Test
	@Disabled
	void testMinus() {
		
	}
	
	@Test
	@Disabled
	void testMultiply() {
		
	}
}
