package com.example.boot_skyscanner.controller;


import com.example.boot_skyscanner.model.Flight;
import com.example.boot_skyscanner.model.FlightBook;
import com.example.boot_skyscanner.service.BookService;
import com.example.boot_skyscanner.service.FlightService;
import com.example.boot_skyscanner.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookController {

    @Autowired
    private FlightService flightService;
    @Autowired
    private BookService bookService;
    private MemberService memberService;

    @PostMapping("/book")
    public String bookTicket(@RequestParam Long flightId, @RequestParam String username, Model model) {
        String message = flightService.addBooking(flightId, username);
        if (message.equals("이미 예약하셨습니다.")) {
            model.addAttribute("errorMessage", message);
        } else {
            model.addAttribute("successMessage", "예약이 완료되었습니다.");
        }// 원래 페이지로 반환
        return "flight/view";
    }
    @GetMapping("/my")
    public String viewMyBookings(Model model, Authentication authentication) {
        if (authentication != null) {
            String username = authentication.getName();
            List<Flight> bookings = flightService.getBookedFlightsByUsername(username);
            model.addAttribute("bookings", bookings);
        }
        return "booking/list";
    }
}
