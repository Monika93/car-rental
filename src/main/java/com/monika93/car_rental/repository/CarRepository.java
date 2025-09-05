package com.monika93.car_rental.repository;

import com.monika93.car_rental.api.CarType;
import com.monika93.car_rental.entity.CarEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<CarEntity, Integer> {

    List<CarEntity> findByType(CarType type);
    long countByType(CarType type);
}
