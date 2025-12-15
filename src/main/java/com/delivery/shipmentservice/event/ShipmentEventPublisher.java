package com.delivery.shipmentservice.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ShipmentEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public ShipmentEventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishStatusChanged(String shipmentId, String status) {
        String message = shipmentId + "|" + status;
        kafkaTemplate.send("shipment.status.changed", shipmentId, message);
    }
}
