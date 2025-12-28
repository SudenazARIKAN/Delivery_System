package com.delivery.notification_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.notification_service.entity.Notification;
import com.delivery.notification_service.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/shipment/{shipmentId}")
    public ResponseEntity<List<Notification>> getNotificationsByShipmentId(@PathVariable String shipmentId) {
        return ResponseEntity.ok(notificationService.getNotificationsByShipmentId(shipmentId));
    }
}
