package com.delivery.notification_service.kafka;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.delivery.notification_service.event.ShipmentStatusChangedEvent;
import com.delivery.notification_service.repository.NotificationRepository;
import com.delivery.notification_service.entity.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShipmentStatusListener {

    private final NotificationRepository notificationRepository;

    @KafkaListener(
        topics = "shipment.status.changed",
        groupId = "notification-service-group"
    )
    public void listen(ShipmentStatusChangedEvent event) {

        log.info("📩 Shipment Status Changed Event received");

        Notification notification = Notification.builder()
            .shipmentId(event.getShipmentId())
            .status(event.getStatus())
            .message(buildMessage(event))
            .createdAt(LocalDateTime.now())
            .build();

        notificationRepository.save(notification);

        log.info("✅ Notification saved for shipmentId={}", event.getShipmentId());
    }

    private String buildMessage(ShipmentStatusChangedEvent event) {
        return "Shipment status changed to "
                + event.getStatus()
                + " (Sender: "
                + event.getSender()
                + ", Receiver: "
                + event.getReceiver()
                + ")";
    }
}
