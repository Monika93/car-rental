package com.monika93.car_rental.repository;

import com.monika93.car_rental.api.CarType;
import com.monika93.car_rental.entity.CarEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CarRepositoryTest {
    @Autowired
    private CarRepository carRepository;

    @Test
    void testFindByType() {
        CarEntity sedan1 = new CarEntity(null, "Toyota", CarType.SEDAN);
        CarEntity sedan2 = new CarEntity(null, "Honda", CarType.SEDAN);
        CarEntity suv = new CarEntity(null, "Ford", CarType.SUV);
        carRepository.save(sedan1);
        carRepository.save(sedan2);
        carRepository.save(suv);

        List<CarEntity> sedans = carRepository.findByType(CarType.SEDAN);
        assertEquals(2, sedans.size());
        assertTrue(sedans.stream().anyMatch(car -> car.getName().equals("Toyota")));
        assertTrue(sedans.stream().anyMatch(car -> car.getName().equals("Honda")));
    }

    @Test
    void testCountByType() {
        carRepository.save(new CarEntity(null, "Toyota", CarType.SEDAN));
        carRepository.save(new CarEntity(null, "Honda", CarType.SEDAN));
        carRepository.save(new CarEntity(null, "Ford", CarType.SUV));

        long sedanCount = carRepository.countByType(CarType.SEDAN);
        long suvCount = carRepository.countByType(CarType.SUV);
        assertEquals(2, sedanCount);
        assertEquals(1, suvCount);
    }

    @Test
    void testFindByTypeEmpty() {
        carRepository.save(new CarEntity(null, "Toyota", CarType.SEDAN));
        List<CarEntity> suvs = carRepository.findByType(CarType.SUV);
        assertTrue(suvs.isEmpty());
    }
}
