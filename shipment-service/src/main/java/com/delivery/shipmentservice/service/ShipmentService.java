package com.delivery.shipmentservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public Shipment createShipment(Shipment shipment) {
        // Yeni entity oluştur, gelen entity'yi direkt kullanma
        Shipment newShipment = new Shipment();
        newShipment.setSender(shipment.getSender());
        newShipment.setReceiver(shipment.getReceiver());
        newShipment.setStatus(ShipmentStatus.CREATED);
        
        // Kaydet
        Shipment saved = repository.save(newShipment);
        
        // Kafka event gönder (transaction dışında)
        try {
            publisher.publishStatusChanged(saved);
        } catch (Exception e) {
            System.err.println("Kafka event gönderilemedi: " + e.getMessage());
            // Hata olsa da kayıt başarılı, bu yüzden exception fırlatma
        }
        
        return saved;
    }

    @Transactional
    public Shipment updateStatus(UUID id, String statusString) {
        Shipment shipment = repository.findById(id).orElse(null);
        if (shipment != null) {
            try {
                ShipmentStatus status = ShipmentStatus.valueOf(statusString.toUpperCase());
                shipment.setStatus(status);
                Shipment updated = repository.save(shipment);

                // Kafka event gönder
                try {
                    publisher.publishStatusChanged(updated);
                } catch (Exception e) {
                    System.err.println("Kafka event gönderilemedi: " + e.getMessage());
                }
                
                return updated;
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status: " + statusString + 
                    ". Valid statuses: CREATED, ASSIGNED, IN_TRANSIT, DELIVERED, CANCELLED");
            }
        }
        return null;
    }

    @Transactional
    public void deleteShipment(UUID id) {
        repository.deleteById(id);
    }
}
