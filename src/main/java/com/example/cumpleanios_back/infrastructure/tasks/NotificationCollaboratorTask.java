package com.example.cumpleanios_back.infrastructure.tasks;

import com.example.cumpleanios_back.application.usecases.NotifyEmployeesUseCase;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationCollaboratorTask {
    private final NotifyEmployeesUseCase notifyEmployeesUseCase;

    public NotificationCollaboratorTask(NotifyEmployeesUseCase notifyEmployeesUseCase) {
        this.notifyEmployeesUseCase = notifyEmployeesUseCase;
    }

    @Scheduled(cron = "0 14 16 * * ?")
    public void notifyCollaborators(){
        System.out.println("Starting employees Notifications");
        this.notifyEmployeesUseCase.execute();
    }
}
