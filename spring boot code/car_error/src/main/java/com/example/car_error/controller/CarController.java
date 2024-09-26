package com.example.car_error.controller;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.car_error.exception.CarInputError;
import com.example.car_error.exception.EmptyCarException;
import com.example.car_error.model.Car;
import com.example.car_error.service.CarService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/cars")
public class CarController {
	private final CarService carService;
	
	@GetMapping("/new")
	public String form(Model model) {
		model.addAttribute("car", new Car());
		return "new-car";
	}
	
	@PostMapping
	public String createCar(@ModelAttribute Car car) {
		carService.save(car);
		return "redirect:/cars";
	}
	
	@ExceptionHandler(CarInputError.class)
	public String handleCarInputError(CarInputError ex,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", ex.getMessage());
		return "redirect:/cars/new";
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public String handleValidException(MethodArgumentNotValidException ex,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", "잘못된 연도 입력");
		return "redirect:/cars/new";
	}
	
	@GetMapping
	public String findAllCars(Model model) {
		model.addAttribute("cars", carService.findAll());
		return "list.html";
	}
	
	@GetMapping("/{id}")
	public String findOneCar(@PathVariable Long id,
			Model model) {
		model.addAttribute("car", carService.findById(id));
		return "car-detail";
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleNoSuchElementException(EmptyCarException ex) {
		return "empty-car";
	}
	
	@ExceptionHandler(EmptyCarException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleEmptyCarException(EmptyCarException ex) {
		return "empty-car";
	}
}
