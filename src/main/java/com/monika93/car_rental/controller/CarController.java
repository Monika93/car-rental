package com.monika93.car_rental.controller;

import com.monika93.car_rental.api.Car;
import com.monika93.car_rental.api.CarType;
import com.monika93.car_rental.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping
    public Car addCar(@RequestBody Car car) {
        return carService.addCar(car.getName(), car.getType());
    }

    @GetMapping
    public java.util.List<Car> getCars(@RequestParam(required = false) CarType type) {
        return carService.getCars(type);
    }
}
