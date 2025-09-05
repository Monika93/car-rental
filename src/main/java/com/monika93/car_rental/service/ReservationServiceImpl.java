package com.monika93.car_rental.service;

import com.monika93.car_rental.api.Car;
import com.monika93.car_rental.api.CarType;
import com.monika93.car_rental.api.Reservation;
import com.monika93.car_rental.entity.CarEntity;
import com.monika93.car_rental.entity.ReservationEntity;
import com.monika93.car_rental.repository.CarRepository;
import com.monika93.car_rental.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarService carService;


    @Override
    public Reservation reserveCar(CarType type, Integer days) {
       LocalDate currentDate = LocalDate.now();
       LocalDate endDate = currentDate.plusDays(days);

       List<Car> availableCars = getAvailableCarsAtTime(type, currentDate, endDate);

       if (availableCars.isEmpty()) {
           return null; // throw exception no cars to reserve
       }

       Car carToReserve = availableCars.get(0);
       CarEntity carEntity = carRepository.findById(carToReserve.getId()).orElseThrow();

       ReservationEntity reservationEntity = new ReservationEntity(null, carEntity, currentDate, endDate);
       reservationRepository.save(reservationEntity);

       return new Reservation(carToReserve, currentDate, endDate);

   }

    private List<Car>  getAvailableCarsAtTime(CarType type, LocalDate startDate, LocalDate endDate) {
        List<Car> carsOfType = carService.getCars(type);
        if (carsOfType.isEmpty())
            return new ArrayList<>(); // throw exception no cars to reserve

        List<ReservationEntity> reservations = reservationRepository.findByCarsInTime(type, startDate, endDate);

        List<Car> availableCars = carsOfType.stream()
                .filter(car -> reservations.stream()
                        .noneMatch(reservationEntity ->
                                reservationEntity.getCar().getId().equals(car.getId())))
                .toList();

        return availableCars;
    }
}
