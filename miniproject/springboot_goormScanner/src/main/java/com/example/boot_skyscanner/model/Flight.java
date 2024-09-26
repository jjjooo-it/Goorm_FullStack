package com.example.boot_skyscanner.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId; // 항공권 id


    @Column(nullable = false)
    private String flightNumber; // 항공 번호 형식 SY123, TO456
    @Column(nullable = false)
    private String airline; // 항공사
    private String departureAirport; // 출발 공항
    private String arrivalAirport; // 도착 공항
    private LocalDateTime departureTime; // 출발 시간
    private LocalDateTime arrivalTime; // 도착 시간

    @Column(nullable = false)
    private int availableSeats; // 이용 가능 좌석
    @Column(nullable = false)
    private int price; // 항공권 가격

}
