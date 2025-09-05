package com.monika93.car_rental.service;

import com.monika93.car_rental.api.Car;
import com.monika93.car_rental.api.CarType;
import com.monika93.car_rental.entity.CarEntity;
import com.monika93.car_rental.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCar() {
        CarEntity savedEntity = new CarEntity(1, "Toyota", CarType.SEDAN);
        when(carRepository.save(ArgumentMatchers.any(CarEntity.class))).thenReturn(savedEntity);

        Car car = carService.addCar("Toyota", CarType.SEDAN);

        assertNotNull(car);
        assertEquals(1, car.getId());
        assertEquals("Toyota", car.getName());
        assertEquals(CarType.SEDAN, car.getType());
        verify(carRepository, times(1)).save(any(CarEntity.class));
    }

    @Test
    void testGetCarsWithType() {
        List<CarEntity> entities = Arrays.asList(
                new CarEntity(1, "Toyota", CarType.SEDAN),
                new CarEntity(2, "Honda", CarType.SEDAN)
        );
        when(carRepository.findByType(CarType.SEDAN)).thenReturn(entities);

        List<Car> cars = carService.getCars(CarType.SEDAN);

        assertEquals(2, cars.size());
        assertEquals("Toyota", cars.get(0).getName());
        verify(carRepository, times(1)).findByType(CarType.SEDAN);
    }

    @Test
    void testGetCarsWithoutType() {
        List<CarEntity> entities = Arrays.asList(
                new CarEntity(1, "Toyota", CarType.SEDAN),
                new CarEntity(2, "Honda", CarType.SUV)
        );
        when(carRepository.findAll()).thenReturn(entities);

        List<Car> cars = carService.getCars(null);

        assertEquals(2, cars.size());
        verify(carRepository, times(1)).findAll();
    }
}
