package com.delivery.shipmentservice.service;

import com.delivery.shipmentservice.event.ShipmentEventPublisher;
import com.delivery.shipmentservice.model.Shipment;
import com.delivery.shipmentservice.repository.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ShipmentService {

    private final ShipmentRepository repository;
    private final ShipmentEventPublisher publisher;

    public ShipmentService(ShipmentRepository repository, ShipmentEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    public List<Shipment> getAllShipments() {
        return repository.findAll();
    }

    public Shipment getById(String id) {
        return repository.findById(id).orElse(null);
    }

    public Shipment createShipment(Shipment shipment) {
        shipment.setId(UUID.randomUUID().toString());
        shipment.setStatus("Created");
        return repository.save(shipment);
    }

    public Shipment updateStatus(String id, String status) {
        Shipment shipment = getById(id);
        if (shipment != null) {
            shipment.setStatus(status);
            repository.save(shipment);

            // Kafka event gönder
            publisher.publishStatusChanged(id, status);
        }
        return shipment;
    }

    public void deleteShipment(String id) {
        repository.deleteById(id);
    }
}
