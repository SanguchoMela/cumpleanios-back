package com.example.cumpleanios_back.infrastructure.tasks;

import com.example.cumpleanios_back.application.usecases.NotifyHumanTalentUseCase;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationHumanTalentTask {
    private final NotifyHumanTalentUseCase notifyHumanTalentUseCase;

    public NotificationHumanTalentTask(NotifyHumanTalentUseCase notifyHumanTalentUseCase) {
        this.notifyHumanTalentUseCase = notifyHumanTalentUseCase;
    }

    @Scheduled(cron = "0 58 10 * * ?")
    public void notifyHumanTalent() {
        System.out.println("Iniciando Notificacion a talento humano");
        this.notifyHumanTalentUseCase.execute();
    }
}
