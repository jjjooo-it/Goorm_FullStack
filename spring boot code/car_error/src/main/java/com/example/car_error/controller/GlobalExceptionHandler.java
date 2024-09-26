package com.example.car_error.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.car_error.exception.NoCarException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(NoCarException.class)
	public String handleNoCarException(NoCarException ex) {
		return "no_car.html";
	}
}
