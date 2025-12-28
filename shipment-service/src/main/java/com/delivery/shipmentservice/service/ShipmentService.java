package com.delivery.shipmentservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.delivery.shipmentservice.event.ShipmentEventPublisher;
import com.delivery.shipmentservice.event.ShipmentStatusChangedEvent;
import com.delivery.shipmentservice.model.Shipment;
import com.delivery.shipmentservice.model.ShipmentStatus;
import com.delivery.shipmentservice.repository.ShipmentRepository;

@Service
public class ShipmentService {

    private final ShipmentRepository repository;
    private final ShipmentEventPublisher publisher;

    public ShipmentService(ShipmentRepository repository,
            ShipmentEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    public List<Shipment> getAllShipments() {
        return repository.findAll();
    }

    public Shipment getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found: " + id));
    }

    @Transactional
    public Shipment createShipment(Shipment shipment) {

        Shipment newShipment = new Shipment();
        newShipment.setSender(shipment.getSender());
        newShipment.setReceiver(shipment.getReceiver());
        newShipment.setStatus(ShipmentStatus.CREATED);

        Shipment saved = repository.save(newShipment);

        publishEvent(saved);

        return saved;
    }

    @Transactional
    public Shipment updateStatus(Long id, ShipmentStatus status) {

        Shipment shipment = repository.findById(id).orElse(null);
        if (shipment == null) {
            return null;
        }

        shipment.setStatus(status);
        Shipment updated = repository.save(shipment);

        ShipmentStatusChangedEvent event = new ShipmentStatusChangedEvent(
                updated.getId().toString(),
                updated.getSender(),
                updated.getReceiver(),
                updated.getStatus().name());

        publisher.publishStatusChanged(event);

        return updated;
    }

    @Transactional
    public void deleteShipment(Long id) {
        repository.deleteById(id);
    }

    // 🔥 EVENT OLUŞTURMA + PUBLISH TEK YER
    private void publishEvent(Shipment shipment) {
        ShipmentStatusChangedEvent event = new ShipmentStatusChangedEvent(
                shipment.getId().toString(),
                shipment.getSender(),
                shipment.getReceiver(),
                shipment.getStatus().name());

        try {
            publisher.publishStatusChanged(event);
        } catch (Exception e) {
            System.err.println("Kafka event gönderilemedi: " + e.getMessage());
        }
    }
}
