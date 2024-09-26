package com.example.boot_skyscanner.controller;

import com.example.boot_skyscanner.model.Flight;
import com.example.boot_skyscanner.service.FlightService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public String listFlights(Model model) {
        List<Flight> flights = flightService.getAllFlights();
        model.addAttribute("flights", flights);
        return "flight/list";
    }

    @GetMapping("/search")
    public String searchFlights(@RequestParam(required = false) String departureAirport,
                                @RequestParam(required = false) String arrivalAirport,
                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureTime,
                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arrivalTime,
                                Model model, RedirectAttributes redirectAttributes) {

        // 모든 필드가 입력되었는지 확인
        if (departureAirport == null ||
                arrivalAirport == null ||
                departureTime == null || arrivalTime == null) {

            redirectAttributes.addFlashAttribute("errorMessage", "모든 필드를 입력해 주세요.");
            return "redirect:/"; // 홈 페이지로 리다이렉트
        }

        // 시간 범위를 설정합니다.
        LocalDateTime startDepartureTime = departureTime.with(LocalTime.MIN);
        LocalDateTime endDepartureTime = departureTime.with(LocalTime.MAX);
        LocalDateTime startArrivalTime = arrivalTime.with(LocalTime.MIN);
        LocalDateTime endArrivalTime = arrivalTime.with(LocalTime.MAX);

        List<Flight> flights = flightService.searchFlightsByAirportsAndTimeRange(departureAirport, arrivalAirport,
                startDepartureTime, endDepartureTime,
                startArrivalTime, endArrivalTime);

        model.addAttribute("flights", flights);
        return "flight/list";
    }
    @GetMapping("/{id}")
    public String viewFlight(@PathVariable Long id, Model model, Authentication authentication) {
        Flight flight = flightService.updateViewCount(id);
        model.addAttribute("flight", flight);
        return "flight/view";
    }

    @PostMapping("/{id}/book")
    public String bookFlight(@PathVariable Long id,
                             @AuthenticationPrincipal UserDetails userDetails,
                             RedirectAttributes redirectAttributes) {
        System.out.println("flight booking!!!");
        String message = flightService.addBooking(id, userDetails.getUsername());
        if (message == null) {
            System.out.println("success message");
            redirectAttributes.addFlashAttribute("successMessage", "예약 성공");
        } else {
            System.out.println("error message: " + message);
            redirectAttributes.addFlashAttribute("errorMessage", message);
        }
        return "redirect:/flights/" + id;
    }
}