package com.example.cumpleanios_back.application.usecases;

import com.example.cumpleanios_back.application.services.EmailService;
import com.example.cumpleanios_back.application.services.UserService;
import com.example.cumpleanios_back.application.services.impl.DateService;
import com.example.cumpleanios_back.application.services.utils.EmailBody;
import com.example.cumpleanios_back.domain.entities.UserEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotifyEmployeesUseCase {
    private final UserService userService;
    private final DateService dateService;
    private final EmailService emailService;

    public NotifyEmployeesUseCase(UserService userService, DateService dateService, EmailService emailService) {
        this.userService = userService;
        this.dateService = dateService;
        this.emailService = emailService;
    }

    public void execute() {
        LocalDate currentMonth = LocalDate.now();
        this.dateService.setDate(currentMonth);
        List<UserEntity> users = userService.findAllByBirthMonth(this.dateService.getMonth());
        var usersWithUpcomingBirthday = usersBirthdayToday(users, currentMonth);

        if (!usersWithUpcomingBirthday.isEmpty()) {
            notifyEmployees(usersWithUpcomingBirthday);
        }
    }

    private List<UserEntity> usersBirthdayToday( List<UserEntity> users, LocalDate currentMonth){
        return  users.stream().filter(user -> {
            LocalDate birthDate = user.getDateBirth().withYear(currentMonth.getYear());
            return this.dateService.lessZeroDay(LocalDate.now(), birthDate);
        }).collect(Collectors.toList());
    }
    private void notifyEmployees(List<UserEntity> employees) {

        for (var employee : employees) {
            Context context = new Context();
            context.setVariable("employee", employee);
            var emailBody = EmailBody.builder()
                    .template("HappyBirthday")
                    .subject("Feliz cumpleaños")
                    .context(context)
                    .recipientList(new String[] {employee.getEmail()})
                    .build();
            this.emailService.sendEmail(emailBody);
        }


    }
}
