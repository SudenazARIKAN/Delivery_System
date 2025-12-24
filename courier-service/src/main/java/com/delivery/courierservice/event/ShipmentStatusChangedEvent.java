package com.delivery.courierservice.event;

public class ShipmentStatusChangedEvent {

    private String shipmentId;
    private String sender;
    private String receiver;
    private String status;

    public ShipmentStatusChangedEvent() {}

    public ShipmentStatusChangedEvent(String shipmentId, String sender, 
                                     String receiver, String status) {
        this.shipmentId = shipmentId;
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
    }

    // Getters
    public String getShipmentId() {
        return shipmentId;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ShipmentStatusChangedEvent{" +
                "shipmentId='" + shipmentId + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}