package com.delivery.shipmentservice.event;

import com.delivery.shipmentservice.model.Shipment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ShipmentEventPublisher {

    private final KafkaTemplate<String, Shipment> kafkaTemplate;

    public ShipmentEventPublisher(KafkaTemplate<String, Shipment> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishStatusChanged(Shipment shipment) {
        kafkaTemplate.send(
                "shipment-status-topic",
                shipment.getId().toString(),
                shipment
        );
    }
}
