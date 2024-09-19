package com.example.cumpleanios_back.application.usecases;

import com.example.cumpleanios_back.application.services.EmailService;
import com.example.cumpleanios_back.application.services.UserService;
import com.example.cumpleanios_back.application.services.impl.DateService;
import com.example.cumpleanios_back.application.services.utils.EmailBody;
import com.example.cumpleanios_back.domain.entities.RoleType;
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
        LocalDate currentDate = this.dateService.getCurrentDate();
        this.dateService.setDate(currentDate);

        List<UserEntity> users = userService.findAllByBirthMonth(this.dateService.getMonth());

        List<UserEntity> usersWithUpcomingBirthday = usersWithUpcomingBirthdays(users,currentDate);

        System.out.println("Users which have theirs birthday tomorrow are: " + usersWithUpcomingBirthday.size());

        if (!usersWithUpcomingBirthday.isEmpty()) {
            notifyHumanTalent(usersWithUpcomingBirthday);
        }

    }
    private List<UserEntity> usersWithUpcomingBirthdays( List<UserEntity> users, LocalDate currentMonth){
        return  users.stream().filter(user -> {
            LocalDate birthDate = user.getDateBirth().withYear(currentMonth.getYear());
            return this.dateService.lessThanOneDay(LocalDate.now(), birthDate);
        }).collect(Collectors.toList());
    }

    private void notifyHumanTalent(List<UserEntity> users) {
        List<String> emails = new ArrayList<>();

        var admins = this.userService.findByRoleType(RoleType.ADMIN);

        for (var admin : admins) {
            emails.add(admin.getEmail());
        }

        Context context = new Context();
        context.setVariable("employees", users);

        var emailBody = EmailBody.builder()
                .template("HumanTalentMail")
                .subject("Recordatorio de Cumpleaños")
                .recipientList(emails.toArray(new String[emails.size()]))
                .context(context)
                .build();

        this.emailService.sendEmail(emailBody);
    }

}
