package com.monika93.car_rental.service;

import com.monika93.car_rental.api.CarType;
import com.monika93.car_rental.api.Reservation;

public interface ReservationService {
    Reservation reserveCar(CarType type, Integer days);
}
