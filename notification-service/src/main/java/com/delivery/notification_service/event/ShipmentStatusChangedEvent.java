package com.delivery.notification_service.event;

public class ShipmentStatusChangedEvent {

    private String shipmentId;
    private String sender;
    private String receiver;
    private String status;

    public ShipmentStatusChangedEvent() {}

    public ShipmentStatusChangedEvent(
            String shipmentId,
            String sender,
            String receiver,
            String status) {
        this.shipmentId = shipmentId;
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
    }

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

}