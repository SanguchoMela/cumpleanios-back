package com.example.cumpleanios_back.application.usecases;

import com.example.cumpleanios_back.application.services.UserService;
import com.example.cumpleanios_back.application.services.impl.DateService;
import com.example.cumpleanios_back.domain.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FindAllByBirthMonth {
    private final UserService userService;
    private final DateService dateService;

    public FindAllByBirthMonth(UserService userService, DateService dateService) {
        this.userService = userService;
        this.dateService = dateService;
    }

    public List<UserEntity> execute(LocalDate currentMonth) {
        this.dateService.setDate(currentMonth);
        return this.userService.findAllByBirthMonth(this.dateService.getMonth());
    }
}
