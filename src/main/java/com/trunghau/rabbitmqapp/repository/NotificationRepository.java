package com.trunghau.rabbitmqapp.repository;

import com.trunghau.rabbitmqapp.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
