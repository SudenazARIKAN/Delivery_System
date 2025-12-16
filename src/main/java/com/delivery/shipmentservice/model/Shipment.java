package com.delivery.shipmentservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private String receiver;

    @Column(nullable = false)
    private String status;

    public Shipment() {}

    public Shipment(String id, String sender, String receiver, String status) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
    }

    // GETTER – SETTER’lar
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getReceiver() { return receiver; }
    public void setReceiver(String receiver) { this.receiver = receiver; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
