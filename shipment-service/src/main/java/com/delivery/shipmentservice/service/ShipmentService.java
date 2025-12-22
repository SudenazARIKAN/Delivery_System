package com.delivery.shipmentservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.delivery.shipmentservice.event.ShipmentEventPublisher;
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

    public Shipment getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));
    }

    public Shipment createShipment(String sender, String receiver) {
        Shipment shipment = new Shipment(sender, receiver);

        Shipment saved = repository.save(shipment);
publisher.publishStatusChanged(saved);
 return saved;

    }

    public Shipment updateStatus(UUID id, ShipmentStatus status) {
        Shipment shipment = getById(id);
        shipment.setStatus(status);

        Shipment updated = repository.save(shipment);

      publisher.publishStatusChanged(updated);

        return updated;
    }

    public void deleteShipment(UUID id) {
        repository.deleteById(id);
    }
}
