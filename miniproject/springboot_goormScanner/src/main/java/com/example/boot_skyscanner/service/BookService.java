package com.example.boot_skyscanner.service;

import com.example.boot_skyscanner.model.Flight;
import com.example.boot_skyscanner.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final FlightRepository flightRepository;

    public String bookFlight(String flightNumber) {
        Flight flight = flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new IllegalArgumentException("Invalid flight number:" + flightNumber));

        int availableSeats = flight.getAvailableSeats();
        if (availableSeats > 0) {
            flight.setAvailableSeats(availableSeats - 1);
            flightRepository.save(flight);
            return "Flight successfully booked!";
        } else {
            return "No available seats for this flight.";
        }
    }
}