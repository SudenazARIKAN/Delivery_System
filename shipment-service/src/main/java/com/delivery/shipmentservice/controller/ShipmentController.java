package com.delivery.shipmentservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.shipmentservice.model.Shipment;
import com.delivery.shipmentservice.model.ShipmentStatus;
import com.delivery.shipmentservice.service.ShipmentService;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {

    private final ShipmentService service;

    public ShipmentController(ShipmentService service) {
        this.service = service;
    }

    // GET /shipments - Tüm gönderileri listele
    @GetMapping
    public ResponseEntity<List<Shipment>> getShipments() {
        return ResponseEntity.ok(shipmentService.getAllShipments());
    }

    // GET /shipments/{id} - ID ile gönderi bul
    @GetMapping("/{id}")
    public ResponseEntity<Shipment> getShipment(@PathVariable UUID id) {
        Shipment shipment = shipmentService.getById(id);
        if (shipment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shipment);
    }

    // POST /shipments - Yeni gönderi oluştur
    @PostMapping
    public ResponseEntity<Shipment> createShipment(@RequestBody Shipment shipment) {
        Shipment created = shipmentService.createShipment(shipment);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT /shipments/{id}/status - Gönderi durumunu güncelle
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable UUID id,
            @RequestParam String status) {
        try {
            Shipment updated = shipmentService.updateStatus(id, status);
            if (updated == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE /shipments/{id} - Gönderiyi sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipment(@PathVariable UUID id) {
        shipmentService.deleteShipment(id);
        return ResponseEntity.noContent().build();
    }
    
    // GET /shipments/statuses - Kullanılabilir tüm statusları listele
    @GetMapping("/statuses")
    public ResponseEntity<ShipmentStatus[]> getAvailableStatuses() {
        return ResponseEntity.ok(ShipmentStatus.values());
    }
}