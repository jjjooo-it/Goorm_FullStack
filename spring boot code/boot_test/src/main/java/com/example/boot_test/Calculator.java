package com.example.boot_test;

public class Calculator {
	// 덧셈 메서드
	public int add(int a, int b) {
		// return a * b; // 잘못된 로직
		return a + b; // 정상 로직
	}
	
	// 나눗셈 메서드
	public int divide(int a, int b) {
		// return a - b; // 잘못된 로직
		return a / b;
	}
}
