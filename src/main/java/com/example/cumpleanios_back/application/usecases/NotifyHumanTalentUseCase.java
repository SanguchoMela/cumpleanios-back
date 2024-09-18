package com.example.cumpleanios_back.application.usecases;

import com.example.cumpleanios_back.application.services.UserService;
import com.example.cumpleanios_back.application.services.impl.DateService;
import com.example.cumpleanios_back.domain.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotifyHumanTalentUseCase {
    private final UserService userService;
    private final DateService dateService;



    public NotifyHumanTalentUseCase(UserService userService, DateService dateService) {
        this.userService = userService;
        this.dateService = dateService;
    }
    public void execute() {
        LocalDate currentMonth = LocalDate.now();
        this.dateService.setDate(currentMonth);

        List<UserEntity> users = userService.findAllByBirthMonth(this.dateService.getMonth());

        List<UserEntity> usersWithUpcomingBirthdays = users.stream()
                .filter(user -> {
                    LocalDate birthDate = user.getDateBirth().withYear(currentMonth.getYear()); // Adjust year to current year
                    return this.dateService.lessThanOneDay(LocalDate.now(), birthDate);
                })
                .collect(Collectors.toList());

        if (!usersWithUpcomingBirthdays.isEmpty()) {
            notifyUsers(usersWithUpcomingBirthdays);
        }
    }

    private void notifyUsers(List<UserEntity> users) {
        users.forEach(user -> {
            System.out.println("Notifying user: " + user.getName() + " whose birthday is soon!");
        });
    }

}
