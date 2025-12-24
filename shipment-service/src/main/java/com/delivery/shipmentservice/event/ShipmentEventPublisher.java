package com.delivery.shipmentservice.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ShipmentEventPublisher {

    private final KafkaTemplate<String, ShipmentStatusChangedEvent> kafkaTemplate;

    public ShipmentEventPublisher(
            KafkaTemplate<String, ShipmentStatusChangedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishStatusChanged(ShipmentStatusChangedEvent event) {
        kafkaTemplate.send(
                "shipment.status.changed",
                event.getShipmentId(),
                event
        );
    }
}
