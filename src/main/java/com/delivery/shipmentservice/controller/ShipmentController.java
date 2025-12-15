package com.delivery.shipmentservice.controller;

import com.delivery.shipmentservice.model.Shipment;
import com.delivery.shipmentservice.service.ShipmentService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    // GET /shipments
    @GetMapping
    public List<Shipment> getShipments() {
        return shipmentService.getAllShipments();
    }

    // GET /shipments/{id}
    @GetMapping("/{id}")
    public Shipment getShipment(@PathVariable String id) {
        return shipmentService.getById(id);
    }

    // POST /shipments
    @PostMapping
    public Shipment createShipment(@RequestBody Shipment shipment) {
        return shipmentService.createShipment(shipment);
    }

    // PUT /shipments/{id}/status
    @PutMapping("/{id}/status")
    public Shipment updateStatus(
            @PathVariable String id,
            @RequestParam String status) {
        return shipmentService.updateStatus(id, status);
    }

    // DELETE /shipments/{id}
    @DeleteMapping("/{id}")
    public void deleteShipment(@PathVariable String id) {
        shipmentService.deleteShipment(id);
    }
}
