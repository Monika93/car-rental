package com.monika93.car_rental.controller;

import com.monika93.car_rental.api.CarType;
import com.monika93.car_rental.api.Reservation;
import com.monika93.car_rental.api.ReservationRegistrationRequest;
import com.monika93.car_rental.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public Reservation reserve(@RequestBody ReservationRegistrationRequest request) {
        return reservationService.reserveCar(request.getCarType(), request.getDays());
    }

}
