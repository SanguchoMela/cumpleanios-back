package com.example.cumpleanios_back.domain.repositories;

import com.example.cumpleanios_back.domain.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
