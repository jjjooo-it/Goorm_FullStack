package com.example.boot_skyscanner.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int passengerId;
    @Column(
            nullable = false
    )
    private String koName;
    @Column(
            nullable = false
    )
    private String enName;
    @Column(
            nullable = false
    )
    private String email;
    @Column(
            nullable = false
    )
    private String mobile;
    @Column(
            nullable = false
    )
    private String gender;

}
