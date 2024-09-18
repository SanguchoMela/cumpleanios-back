package com.example.cumpleanios_back.application.services.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public LocalDate minusDate(LocalDate startDate, LocalDate endDate){
        return startDate;
    }
}
