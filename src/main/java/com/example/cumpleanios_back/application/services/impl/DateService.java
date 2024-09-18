package com.example.cumpleanios_back.application.services.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class DateService {
    private LocalDate date;

    public LocalDate firstMonthDay(){
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }
    public LocalDate lastMonthDay(){
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    public Integer getMonth(){
        return date.getMonth().getValue();
    }

    public boolean lessThanOneDay(LocalDate startDate, LocalDate endDate){
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return daysBetween >= 0 && daysBetween <= 1;
    }

}
