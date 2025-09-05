package com.monika93.car_rental.service;

import com.monika93.car_rental.api.Car;
import com.monika93.car_rental.api.CarType;

import java.util.List;

public interface CarService {
    Car addCar(String name, CarType type);

    List<Car> getCars(CarType type);
}
