package com.example.boot_skyscanner.controller;


import com.example.boot_skyscanner.model.Flight;
import com.example.boot_skyscanner.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/flights")
public class AdminController {
    private final FlightService flightService;

    public AdminController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public String listFlights(Model model) {
        model.addAttribute("flights", flightService.getAllFlights());
        return "admin/flight/list";
    }

    @GetMapping("/add")
    public String showAddFlightForm(Model model) {
        model.addAttribute("flight", new Flight());
        return "admin/flight/form";
    }

    @PostMapping("/add")
    public String addFlight(@ModelAttribute Flight flight) {
        flightService.addFlight(
                flight.getFlightNumber(),
                flight.getAirline(),
                flight.getDepartureAirport(),
                flight.getArrivalAirport(),
                flight.getDepartureTime(),
                flight.getArrivalTime(),
                flight.getAvailableSeats(),
                flight.getPrice());
        return "redirect:/admin/flights";
    }

    @GetMapping("/edit/{flightId}")
    public String showEditFlightForm(@PathVariable Long flightId, Model model) {
        model.addAttribute("flight", flightService.getFlightById(flightId));
        return "admin/flight/form";
    }
    @PostMapping("/edit/{flightId}")
    public String updateFlight(@PathVariable("flightId") Long flightId, @ModelAttribute("flight") Flight flight) {
        Flight existingFlight = flightService.getFlightById(flightId);

        existingFlight.setFlightNumber(flight.getFlightNumber());
        existingFlight.setAirline(flight.getAirline());
        existingFlight.setDepartureAirport(flight.getDepartureAirport());
        existingFlight.setArrivalAirport(flight.getArrivalAirport());
        existingFlight.setDepartureTime(flight.getDepartureTime());
        existingFlight.setArrivalTime(flight.getArrivalTime());
        existingFlight.setAvailableSeats(flight.getAvailableSeats());
        existingFlight.setPrice(flight.getPrice());

        flightService.updateFlight(flightId, existingFlight);
        return "redirect:/admin/flights";
    }

    @PostMapping("/delete/{flightId}")
    public String deleteFlight(@PathVariable Long flightId) {
        flightService.deleteFlight(flightId);
        return "redirect:/admin/flights";
    }
}
