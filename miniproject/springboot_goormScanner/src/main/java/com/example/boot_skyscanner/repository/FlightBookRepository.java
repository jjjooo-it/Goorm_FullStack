package com.example.boot_skyscanner.repository;

import com.example.boot_skyscanner.model.Flight;
import com.example.boot_skyscanner.model.FlightBook;
import com.example.boot_skyscanner.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FlightBookRepository extends JpaRepository<FlightBook, Long> {

    // 특정 Flight와 Member로 FlightBook을 찾는 메서드
    Optional<FlightBook> findByFlightAndMember(Flight flight, Member member);

    // 특정 Member가 예약한 모든 Flight를 찾는 메서드
    @Query("SELECT fb.flight FROM FlightBook fb WHERE fb.member = :member")
    List<Flight> findFlightsByMember(@Param("member") Member member);
}