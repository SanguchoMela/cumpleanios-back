package com.example.cumpleanios_back.application.usecases;

import com.example.cumpleanios_back.application.services.EmailService;
import com.example.cumpleanios_back.application.services.UserService;
import com.example.cumpleanios_back.application.services.impl.DateService;
import com.example.cumpleanios_back.application.services.utils.EmailBody;
import com.example.cumpleanios_back.domain.entities.UserEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotifyHumanTalentUseCase {
    private final UserService userService;
    private final DateService dateService;

    private final EmailService emailService;

    public NotifyHumanTalentUseCase(UserService userService, DateService dateService, EmailService emailService) {
        this.userService = userService;
        this.dateService = dateService;
        this.emailService = emailService;
    }

    public void execute() {
        LocalDate currentMonth = LocalDate.now();
        this.dateService.setDate(currentMonth);

        List<UserEntity> users = userService.findAllByBirthMonth(this.dateService.getMonth());

        List<UserEntity> usersWithUpcomingBirthdays = users.stream()
                .filter(user -> {
                    LocalDate birthDate = user.getDateBirth().withYear(currentMonth.getYear());
                    return this.dateService.lessThanOneDay(LocalDate.now(), birthDate);
                })
                .collect(Collectors.toList());

        System.out.println("Users which have theirs birthday tomorrow are: " + usersWithUpcomingBirthdays.size());

        if (!usersWithUpcomingBirthdays.isEmpty()) {
            notifyUsers(usersWithUpcomingBirthdays);
        }
    }

    private void notifyUsers(List<UserEntity> users) {
        List<String> emails = new ArrayList<>();
        System.out.println("Start Notification");
        users.forEach(user -> {
            emails.add(user.getEmail());
        });
        var emailBody = EmailBody.builder()
                .template("HumanResour")
                .subject("Feliz Cumpleaños")
                .recipientList(emails.toArray(new String[emails.size()]))
                .build();

        this.emailService.sendEmail(emailBody);
    }

}
