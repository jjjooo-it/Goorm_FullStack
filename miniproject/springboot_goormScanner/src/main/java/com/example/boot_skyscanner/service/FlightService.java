package com.example.boot_skyscanner.service;

import com.example.boot_skyscanner.model.Flight;
import com.example.boot_skyscanner.model.Member;
import com.example.boot_skyscanner.model.FlightBook;
import com.example.boot_skyscanner.repository.FlightRepository;
import com.example.boot_skyscanner.repository.MemberRepository;
import com.example.boot_skyscanner.repository.FlightBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;
    private final MemberRepository memberRepository;
    private final FlightBookRepository flightBookRepository;

    // 모든 항공편을 가져오는 메서드
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }
    public void addFlight(String flightNumber,
                          String airline,
                          String departureAirport,
                          String arrivalAirport,
                          LocalDateTime departureTime,
                          LocalDateTime arrivalTime,
                          int availableSeats,
                          int price) {
        Flight flight = new Flight();
        flight.setFlightNumber(flightNumber);
        flight.setAirline(airline);
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        flight.setAvailableSeats(availableSeats);
        flight.setPrice(price);
        flightRepository.save(flight);
    }

    public Flight getFlightById(Long flightId) {
        return flightRepository.findById(flightId).orElseThrow(() -> new IllegalArgumentException("Invalid flight id:" + flightId));
    }
    public void updateFlight(Long flightId, Flight flight) {
        flight.setFlightId(flightId);
        flightRepository.save(flight);  // 기존 항공편을 덮어씀
    }

    public void  deleteFlight(Long flightId) {
        flightRepository.deleteById(flightId);
    }



    public String addBooking(Long flightId, String username) {
        System.out.println("111");
        Flight flight = getFlightById(flightId);
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username:" + username));
        System.out.println("222");
        int availableSeats = flight.getAvailableSeats();
        if (availableSeats <= 0) {
            return "예약이 불가능합니다: 좌석이 없습니다.";
        }
        System.out.println("333");
        if (flightBookRepository.findByFlightAndMember(flight, member).isEmpty()) {
            flight.setAvailableSeats(availableSeats - 1);
            flightRepository.save(flight);

            FlightBook booking = new FlightBook();
            booking.setFlight(flight);
            booking.setMember(member);
            flightBookRepository.save(booking);

            return null; // 성공적으로 예약된 경우
        }
        System.out.println("444");
        return "이미 예약하셨습니다."; // 중복 예약 방지
    }

    // 사용자의 예약한 항공편을 가져오는 메서드
    public List<Flight> getBookedFlightsByUsername(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username:" + username));
        return flightBookRepository.findFlightsByMember(member);
    }


    public Flight updateViewCount(Long id) {
        Flight  flight= getFlightById(id);
        return flightRepository.save(flight);
    }

    public List<Flight> searchFlightsByAirportsAndTimeRange(String departureAirport, String arrivalAirport,
                                                            LocalDateTime startDepartureTime, LocalDateTime endDepartureTime,
                                                            LocalDateTime startArrivalTime, LocalDateTime endArrivalTime) {
        return flightRepository.findByDepartureAirportAndArrivalAirportAndDepartureTimeBetweenAndArrivalTimeBetween(
                departureAirport, arrivalAirport,
                startDepartureTime, endDepartureTime,
                startArrivalTime, endArrivalTime);
    }

}