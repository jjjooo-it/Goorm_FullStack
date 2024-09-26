package com.example.boot_skyscanner.repository;

import com.example.boot_skyscanner.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;



import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    Optional<Flight> findByFlightNumber(String flightNumber);
    List<Flight> findByDepartureAirportAndArrivalAirportAndDepartureTimeBetweenAndArrivalTimeBetween(
            String departureAirport,
            String arrivalAirport,
            LocalDateTime startDepartureTime,
            LocalDateTime endDepartureTime,
            LocalDateTime startArrivalTime,
            LocalDateTime endArrivalTime);
}




