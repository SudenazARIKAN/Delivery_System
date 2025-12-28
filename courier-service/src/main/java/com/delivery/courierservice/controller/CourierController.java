package com.delivery.courierservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.courierservice.model.Courier;
import com.delivery.courierservice.model.CourierStatus;
import com.delivery.courierservice.service.CourierService;

@RestController
@RequestMapping("/couriers")
public class CourierController {

    private final CourierService courierService;

    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }

    // GET /couriers - Tüm courier'ları listele
    @GetMapping
    public ResponseEntity<List<Courier>> getAllCouriers() {
        return ResponseEntity.ok(courierService.getAllCouriers());
    }

    // GET /couriers/available - Müsait courier'ları listele
    @GetMapping("/available")
    public ResponseEntity<List<Courier>> getAvailableCouriers() {
        return ResponseEntity.ok(courierService.getAvailableCouriers());
    }

    // GET /couriers/{id} - ID ile courier bul
    @GetMapping("/{id}")
    public ResponseEntity<Courier> getCourier(@PathVariable Long id) {
        Courier courier = courierService.getById(id);
        if (courier == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(courier);
    }

    // POST /couriers - Yeni courier oluştur
    @PostMapping
    public ResponseEntity<Courier> createCourier(@RequestBody Courier courier) {
        Courier created = courierService.createCourier(courier);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT /couriers/{id}/assign - Courier'a shipment ata
    @PutMapping("/{id}/assign")
    public ResponseEntity<?> assignShipment(
            @PathVariable Long id,
            @RequestBody AssignRequest request) {
        Courier updated = courierService.assignShipment(id, request.getShipmentId());
        if (updated == null) {
            return ResponseEntity.badRequest()
                    .body("Courier not found or not available");
        }
        return ResponseEntity.ok(updated);
    }

    // PUT /couriers/{id}/status - Courier durumunu güncelle
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestBody StatusRequest request) {
        try {
            Courier updated = courierService.updateStatus(id, request.getStatus());
            if (updated == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT /couriers/{id}/location - Courier konumunu güncelle
    @PutMapping("/{id}/location")
    public ResponseEntity<?> updateLocation(
            @PathVariable Long id,
            @RequestBody LocationRequest request) {
        Courier updated = courierService.updateLocation(id, request.getLocation());
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // DELETE /couriers/{id} - Courier'ı sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourier(@PathVariable Long id) {
        courierService.deleteCourier(id);
        return ResponseEntity.noContent().build();
    }

    // GET /couriers/statuses - Mevcut statusları listele
    @GetMapping("/statuses")
    public ResponseEntity<CourierStatus[]> getAvailableStatuses() {
        return ResponseEntity.ok(CourierStatus.values());
    }

    // Request DTOs
    public static class AssignRequest {
        private Long shipmentId;

        public Long getShipmentId() {
            return shipmentId;
        }

        public void setShipmentId(Long shipmentId) {
            this.shipmentId = shipmentId;
        }
    }

    public static class StatusRequest {
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class LocationRequest {
        private String location;

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}