package com.example.car_error.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.car_error.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

}
