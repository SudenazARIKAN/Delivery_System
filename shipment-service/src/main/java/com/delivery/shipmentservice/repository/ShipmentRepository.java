package com.delivery.shipmentservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.delivery.shipmentservice.model.Shipment;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, UUID> {

}