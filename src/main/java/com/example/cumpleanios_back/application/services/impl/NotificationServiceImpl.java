package com.example.cumpleanios_back.application.services.impl;

import com.example.cumpleanios_back.application.services.NotificationService;
import com.example.cumpleanios_back.domain.entities.Notification;
import com.example.cumpleanios_back.domain.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification create(Notification notification) {
        return this.notificationRepository.save(notification);
    }
}
