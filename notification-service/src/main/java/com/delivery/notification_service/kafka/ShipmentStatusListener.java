package com.delivery.notification_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.delivery.notification_service.event.ShipmentStatusChangedEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShipmentStatusListener {

    @KafkaListener(topics = "shipment.status.changed")
    public void listen(ShipmentStatusChangedEvent event) {

        log.info("📩 Shipment Status Changed");
        log.info("ID: {}", event.getShipmentId());
        log.info("Sender: {}", event.getSender());
        log.info("Receiver: {}", event.getReceiver());
        log.info("Status: {}", event.getStatus());
    }
}