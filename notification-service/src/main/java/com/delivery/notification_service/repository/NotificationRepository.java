package com.delivery.notification_service.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.delivery.notification_service.entity.Notification;

public interface NotificationRepository
                extends JpaRepository<Notification, UUID> {

        java.util.List<Notification> findByShipmentId(String shipmentId);
}
