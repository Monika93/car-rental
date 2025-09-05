package com.monika93.car_rental.repository;

import com.monika93.car_rental.api.CarType;
import com.monika93.car_rental.entity.ReservationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends CrudRepository<ReservationEntity, Integer> {

    @Query("SELECT r FROM ReservationEntity r WHERE r.car.type = :carType AND " +
            "((r.startDate >= :startDate AND r.startDate >= :endDate) OR " +  //  r.startDate inside the range
            "(r.endDate >= :startDate AND r.endDate <= :endDate) OR " + // r.endDate inside the range
            "(r.startDate <= :startDate AND r.endDate >= :endDate))")  // r range is bigger than the given range
    List<ReservationEntity> findByCarsInTime(CarType carType, LocalDate startDate, LocalDate endDate);
}
