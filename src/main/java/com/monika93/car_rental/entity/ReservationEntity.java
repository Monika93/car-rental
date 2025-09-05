package com.monika93.car_rental.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

@Entity
@Table(name="reservations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cars_id")
    private CarEntity car;
    private LocalDate startDate;
    private LocalDate endDate;

}
