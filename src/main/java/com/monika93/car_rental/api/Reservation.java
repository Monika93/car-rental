package com.monika93.car_rental.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reservation {

    private Car car;
    private LocalDate startDate;
    private LocalDate endDate;

    public Integer getDays() {
        if (Objects.nonNull(startDate) & Objects.nonNull(endDate)) {
            Period period = Period.between(startDate, endDate);
            return period.getDays();
        }
        // TODO is it real return value?
        return 0;
    }

}
