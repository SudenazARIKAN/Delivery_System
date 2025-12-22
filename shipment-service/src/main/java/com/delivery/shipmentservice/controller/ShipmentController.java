package com.delivery.shipmentservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public List<Shipment> getAll() {
        return service.getAllShipments();
    }

    @GetMapping("/{id}")
    public Shipment getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    public Shipment create(@RequestBody Shipment shipment) {
        return service.createShipment(
                shipment.getSender(),
                shipment.getReceiver()
        );
    }

    @PutMapping("/{id}/status")
    public Shipment updateStatus(
            @PathVariable UUID id,
            @RequestParam ShipmentStatus status
    ) {
        return service.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.deleteShipment(id);
    }
}
