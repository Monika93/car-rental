package com.monika93.car_rental.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationRegistrationRequest {

    private CarType carType;
    private Integer days;
}
