package com.delivery.shipmentservice.service;

import com.delivery.shipmentservice.event.ShipmentEventPublisher;
import com.delivery.shipmentservice.model.Shipment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ShipmentService {

    private final List<Shipment> shipments = new ArrayList<>();
    private final ShipmentEventPublisher publisher;   // 🔹 1️⃣ EKLENDİ

    // 🔹 2️⃣ CONSTRUCTOR DEĞİŞTİ
    public ShipmentService(ShipmentEventPublisher publisher) {
        this.publisher = publisher;

        shipments.add(new Shipment("1", "Ali", "Veli", "Delivered"));
        shipments.add(new Shipment("2", "Ayşe", "Fatma", "In Transit"));
    }

    public List<Shipment> getAllShipments() {
        return shipments;
    }

    public Shipment getById(String id) {
        return shipments.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Shipment createShipment(Shipment shipment) {
        shipment.setId(UUID.randomUUID().toString());
        shipment.setStatus("Created");
        shipments.add(shipment);
        return shipment;
    }

    // 🔹 3️⃣ SADECE BURAYA 1 SATIR EKLİYORUZ
    public Shipment updateStatus(String id, String status) {
        Shipment shipment = getById(id);
        if (shipment != null) {
            shipment.setStatus(status);

            // 🚀 Kafka mesajı
            publisher.publishStatusChanged(id, status);
        }
        return shipment;
    }

    public void deleteShipment(String id) {
        shipments.removeIf(s -> s.getId().equals(id));
    }
}
