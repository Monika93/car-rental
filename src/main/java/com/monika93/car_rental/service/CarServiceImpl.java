package com.monika93.car_rental.service;

import com.monika93.car_rental.api.Car;
import com.monika93.car_rental.api.CarType;
import com.monika93.car_rental.entity.CarEntity;
import com.monika93.car_rental.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    CarRepository carRepository;

    @Override
    public Car addCar(String name, CarType type) {

        CarEntity carEntity = new CarEntity(null, name, type);
        carEntity = carRepository.save(carEntity);

        return new Car(carEntity.getId(), carEntity.getName(), carEntity.getType());
    }

    @Override
    public List<Car> getCars(CarType type) {
        List<CarEntity> carEntities;
        if (type == null) {
            carEntities = (List<CarEntity>) carRepository.findAll();
        } else {
            carEntities = carRepository.findByType(type);
        }
        List<Car> cars = new ArrayList<>();
        for (CarEntity carEntity : carEntities) {
            cars.add(new Car(carEntity.getId(), carEntity.getName(), carEntity.getType()));
        }
        return cars;
    }
}
