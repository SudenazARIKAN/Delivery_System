package com.delivery.shipmentservice.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "shipment")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private String receiver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShipmentStatus status;

    public Shipment() {}

    public Shipment(UUID id, String sender, String receiver, ShipmentStatus status) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
    }

    // Getters and Setters
    public UUID getId() { 
        return id; 
    }
    
    public void setId(UUID id) { 
        this.id = id; 
    }

    public String getSender() { 
        return sender; 
    }
    
    public void setSender(String sender) { 
        this.sender = sender; 
    }

    public String getReceiver() { 
        return receiver; 
    }
    
    public void setReceiver(String receiver) { 
        this.receiver = receiver; 
    }

    public ShipmentStatus getStatus() { 
        return status; 
    }
    
    public void setStatus(ShipmentStatus status) { 
        this.status = status; 
    }
}