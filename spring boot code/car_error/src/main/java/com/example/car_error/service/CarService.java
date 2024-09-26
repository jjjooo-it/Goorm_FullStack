package com.example.car_error.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.car_error.exception.CarInputError;
import com.example.car_error.exception.EmptyCarException;
import com.example.car_error.exception.NoCarException;
import com.example.car_error.model.Car;
import com.example.car_error.repository.CarRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarService {
	private final CarRepository carRepository;

	public void save(Car car) {
		if (car.getModel().isEmpty()) {
			log.info("car model is empty");
			throw new CarInputError("모델이 빔");
		}
		if (car.getColor().isEmpty()) {
			log.info("car color is empty");
			throw new CarInputError("색상이 빔");
		}
		if (car.getProductionYear() < 2000) {
			log.info("2000년 미만");
			throw new CarInputError("연도가 너무 작음");
		}
		carRepository.save(car);
	}

	public List<Car> findAll() {
		List<Car> cars = carRepository.findAll();
		if (cars.isEmpty()) {
			throw new NoCarException();
		}
		return cars;
	}

	public Car findById(Long id) {
//		Optional<Car> car = carRepository.findById(id);
//		if (car.isEmpty()) {
//			throw new EmptyCarException();
//		}
//		return car.get();
		Car car = carRepository.findById(id).orElseThrow();
		// NoSuchElementException -> throw.
		return car;
	}
}
