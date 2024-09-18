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

    @Scheduled(cron = "0 43 16 * * ?")
    public void reportCurrentTime() {
        this.notifyHumanTalentUseCase.execute();
        System.out.println("Esta es una tarea programada");
    }
}
