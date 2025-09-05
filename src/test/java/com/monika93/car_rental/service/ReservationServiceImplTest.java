package com.monika93.car_rental.service;

import com.monika93.car_rental.api.Car;
import com.monika93.car_rental.api.CarType;
import com.monika93.car_rental.api.Reservation;
import com.monika93.car_rental.entity.CarEntity;
import com.monika93.car_rental.entity.ReservationEntity;
import com.monika93.car_rental.repository.CarRepository;
import com.monika93.car_rental.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceImplTest {
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private CarRepository carRepository;
    @Mock
    private CarService carService;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void reserveCar_shouldReturnReservation_whenCarAvailable() {
        CarType type = CarType.SEDAN;
        int days = 2;
        LocalDate now = LocalDate.now();
        LocalDate end = now.plusDays(days);
        Car car = new Car(1, "Toyota", type);
        CarEntity carEntity = new CarEntity(1, "Toyota", type);
        ReservationEntity reservationEntity = new ReservationEntity(null, carEntity, now, end);

        when(carService.getCars(type)).thenReturn(List.of(car));
        when(reservationRepository.findByCarsInTime(type, now, end)).thenReturn(Collections.emptyList());
        when(carRepository.findById(1)).thenReturn(Optional.of(carEntity));
        when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(reservationEntity);

        Reservation reservation = reservationService.reserveCar(type, days);

        assertNotNull(reservation);
        assertEquals(car, reservation.getCar());
        assertEquals(now, reservation.getStartDate());
        assertEquals(end, reservation.getEndDate());
    }

    @Test
    void reserveCar_shouldReturnNull_whenNoAvailableCars() {
        CarType type = CarType.SUV;
        int days = 3;
        LocalDate now = LocalDate.now();
        LocalDate end = now.plusDays(days);
        when(carService.getCars(type)).thenReturn(Collections.emptyList());

        Reservation reservation = reservationService.reserveCar(type, days);
        assertNull(reservation);
    }

    @Test
    void reserveCar_shouldNotReserve_whenAllCarsReserved() {
        CarType type = CarType.SEDAN;
        int days = 1;
        LocalDate now = LocalDate.now();
        LocalDate end = now.plusDays(days);
        Car car = new Car(2, "Honda", type);
        CarEntity carEntity = new CarEntity(2, "Honda", type);
        ReservationEntity existingReservation = new ReservationEntity(1, carEntity, now, end);

        when(carService.getCars(type)).thenReturn(List.of(car));
        when(reservationRepository.findByCarsInTime(type, now, end)).thenReturn(List.of(existingReservation));

        Reservation reservation = reservationService.reserveCar(type, days);
        assertNull(reservation);
    }

    @Test
    void reserveCar_shouldSaveReservationEntity() {
        CarType type = CarType.SEDAN;
        int days = 2;
        LocalDate now = LocalDate.now();
        LocalDate end = now.plusDays(days);
        Car car = new Car(3, "Ford", type);
        CarEntity carEntity = new CarEntity(3, "Ford", type);

        when(carService.getCars(type)).thenReturn(List.of(car));
        when(reservationRepository.findByCarsInTime(type, now, end)).thenReturn(Collections.emptyList());
        when(carRepository.findById(3)).thenReturn(Optional.of(carEntity));
        when(reservationRepository.save(any(ReservationEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        reservationService.reserveCar(type, days);

        ArgumentCaptor<ReservationEntity> captor = ArgumentCaptor.forClass(ReservationEntity.class);
        verify(reservationRepository).save(captor.capture());
        ReservationEntity savedEntity = captor.getValue();
        assertEquals(carEntity, savedEntity.getCar());
        assertEquals(now, savedEntity.getStartDate());
        assertEquals(end, savedEntity.getEndDate());
    }

    @Test
    void reserveCar_shouldThrowException_whenCarNotFound() {
        CarType type = CarType.SEDAN;
        int days = 1;
        LocalDate now = LocalDate.now();
        LocalDate end = now.plusDays(days);
        Car car = new Car(4, "Mazda", type);
        when(carService.getCars(type)).thenReturn(List.of(car));
        when(reservationRepository.findByCarsInTime(type, now, end)).thenReturn(Collections.emptyList());
        when(carRepository.findById(4)).thenReturn(Optional.empty());

        assertThrows(java.util.NoSuchElementException.class, () -> reservationService.reserveCar(type, days));
    }
}
