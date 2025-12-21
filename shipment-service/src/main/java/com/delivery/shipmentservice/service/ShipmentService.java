package com.delivery.shipmentservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.delivery.shipmentservice.event.ShipmentEventPublisher;
import com.delivery.shipmentservice.model.Shipment;
import com.delivery.shipmentservice.repository.ShipmentRepository;

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
        shipment.setStatus("CREATED");
        return repository.save(shipment);
    }

    public Shipment updateStatus(String id, String status) {
        Shipment shipment = getById(id);
        if (shipment != null) {
            shipment.setStatus(status);
            repository.save(shipment);

            // Kafka event gönder
            publisher.publishStatusChanged(shipment);
        }
        return shipment;
    }

    public void deleteShipment(String id) {
        repository.deleteById(id);
    }

}
