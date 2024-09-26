package com.example.boot_skyscanner.repository;

import com.example.boot_skyscanner.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
