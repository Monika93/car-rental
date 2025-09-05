package com.monika93.car_rental.entity;

import com.monika93.car_rental.api.CarType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="cars")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String name;
    private CarType type;

}
