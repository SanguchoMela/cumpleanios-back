package com.example.cumpleanios_back.application.usecases;

import com.example.cumpleanios_back.application.services.UserService;
import com.example.cumpleanios_back.application.services.impl.DateService;
import com.example.cumpleanios_back.domain.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class NotifyHumanTalentUseCase {
    private final UserService userService;
    private final DateService dateService;

    public NotifyHumanTalentUseCase(UserService userService, DateService dateService) {
        this.userService = userService;
        this.dateService = dateService;
    }
    public void execute(){
        // Extract current month
        LocalDate  currentMont = LocalDate.now();
        this.dateService.setDate(currentMont);

        // Extract employees which will have its birthday current mont
        List<UserEntity> users = userService.findAllByBirthMonth(this.dateService.getMonth());

        // Filter users which birthdate is less than 1 day

    }

    public void birthdateLessThanOne(LocalDate userBirthDate, LocalDate currentDate){
        var days = Period.between(userBirthDate, currentDate).getDays();
    }
}
