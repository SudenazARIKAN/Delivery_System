package com.delivery.notification_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.delivery.notification_service.entity.Notification;
import com.delivery.notification_service.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getNotificationsByShipmentId(String shipmentId) {
        return notificationRepository.findByShipmentId(shipmentId);
    }
}
