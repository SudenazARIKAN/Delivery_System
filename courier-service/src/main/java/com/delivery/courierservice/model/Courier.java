package com.delivery.courierservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "courier")
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourierStatus status;

    @Column(name = "current_location")
    private String currentLocation;

    @Column(name = "assigned_shipment_id")
    private Long assignedShipmentId;

    public Courier() {
    }

    public Courier(Long id, String name, String phone, CourierStatus status,
            String currentLocation, Long assignedShipmentId) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.status = status;
        this.currentLocation = currentLocation;
        this.assignedShipmentId = assignedShipmentId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public CourierStatus getStatus() {
        return status;
    }

    public void setStatus(CourierStatus status) {
        this.status = status;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Long getAssignedShipmentId() {
        return assignedShipmentId;
    }

    public void setAssignedShipmentId(Long assignedShipmentId) {
        this.assignedShipmentId = assignedShipmentId;
    }
}