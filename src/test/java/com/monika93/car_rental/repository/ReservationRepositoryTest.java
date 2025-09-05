package com.monika93.car_rental.repository;

import com.monika93.car_rental.api.CarType;
import com.monika93.car_rental.entity.CarEntity;
import com.monika93.car_rental.entity.ReservationEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CarRepository carRepository;

    @Test
    void shouldFindReservationsInTimeRange() {
        CarEntity car = new CarEntity();
        car.setType(CarType.SEDAN);
        carRepository.save(car);

        ReservationEntity reservation = new ReservationEntity();
        reservation.setCar(car);
        reservation.setStartDate(LocalDate.of(2024, 6, 10));
        reservation.setEndDate(LocalDate.of(2024, 6, 15));
        reservationRepository.save(reservation);

        List<ReservationEntity> found = reservationRepository.findByCarsInTime(
                CarType.SEDAN,
                LocalDate.of(2024, 6, 9),
                LocalDate.of(2024, 6, 16)
        );

        assertThat(found).hasSize(1);
        assertThat(found.get(0).getCar().getType()).isEqualTo(CarType.SEDAN);
    }
}
