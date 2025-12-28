package com.delivery.courierservice.event;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.delivery.courierservice.model.Courier;
import com.delivery.courierservice.service.CourierService;

@Service
public class ShipmentEventListener {

    private final CourierService courierService;

    public ShipmentEventListener(CourierService courierService) {
        this.courierService = courierService;
    }

    /**
     * Shipment service'den gelen event'leri dinler
     * Yeni bir shipment oluşturulduğunda otomatik olarak müsait bir courier'a atar
     */
    @KafkaListener(topics = "shipment.status.changed", groupId = "courier-service-group")
    public void handleShipmentStatusChanged(ShipmentStatusChangedEvent event) {
        System.out.println("📦 Shipment event alındı: " + event);

        try {
            // Sadece CREATED statusundaki shipment'lar için courier ata
            if ("CREATED".equals(event.getStatus())) {
                assignCourierToShipment(event);
            }

            // DELIVERED statusundaki shipment'lar için courier'ı serbest bırak
            if ("DELIVERED".equals(event.getStatus())) {
                releaseCourier(event.getShipmentId());
            }

        } catch (Exception e) {
            System.err.println("❌ Event işlenirken hata: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Müsait bir courier bulup shipment'ı atar
     */
    private void assignCourierToShipment(ShipmentStatusChangedEvent event) {
        // Müsait courier'ları bul
        List<Courier> availableCouriers = courierService.getAvailableCouriers();

        if (availableCouriers.isEmpty()) {
            System.out.println("⚠️ Müsait courier bulunamadı! Shipment ID: " + event.getShipmentId());
            return;
        }

        // İlk müsait courier'ı al
        Courier courier = availableCouriers.get(0);

        // Shipment'ı courier'a ata
        try {
            Long shipmentId = Long.parseLong(event.getShipmentId());
            courierService.assignShipment(courier.getId(), shipmentId);

            System.out.println("✅ Shipment " + event.getShipmentId() +
                    " courier " + courier.getName() + " tarafından alındı!");
        } catch (NumberFormatException e) {
            System.err.println("Invalid shipment ID format: " + event.getShipmentId());
        }
    }

    /**
     * Teslimat tamamlandığında courier'ı serbest bırak
     */
    private void releaseCourier(String shipmentIdStr) {
        try {
            Long shipmentId = Long.parseLong(shipmentIdStr);

            // Tüm courier'ları kontrol et
            List<Courier> allCouriers = courierService.getAllCouriers();

            for (Courier courier : allCouriers) {
                // Ensure safe comparison of Longs
                if (shipmentId.equals(courier.getAssignedShipmentId())) {
                    // Courier'ı tekrar müsait yap
                    courierService.updateStatus(courier.getId(), "AVAILABLE");
                    System.out.println("✅ Courier " + courier.getName() + " tekrar müsait!");
                    break;
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid shipment ID format: " + shipmentIdStr);
        }
    }
}