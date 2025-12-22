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
        ShipmentStatusChangedEvent event =
                new ShipmentStatusChangedEvent(
                        shipment.getId().toString(),      // UUID'yi String'e çevir
                        shipment.getSender(),
                        shipment.getReceiver(),
                        shipment.getStatus().name()       // Enum'u String'e çevir
                );

        kafkaTemplate.send("shipment.status.changed", shipment.getId().toString(), event);
    }
}
