package com.delivery.courierservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.delivery.courierservice.model.Courier;
import com.delivery.courierservice.model.CourierStatus;
import com.delivery.courierservice.repository.CourierRepository;

@Service
public class CourierService {

    private final CourierRepository repository;

    public CourierService(CourierRepository repository) {
        this.repository = repository;
    }

    public List<Courier> getAllCouriers() {
        return repository.findAll();
    }

    public List<Courier> getAvailableCouriers() {
        return repository.findByStatus(CourierStatus.AVAILABLE);
    }

    public Courier getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public Courier createCourier(Courier courier) {
        Courier newCourier = new Courier();
        newCourier.setName(courier.getName());
        newCourier.setPhone(courier.getPhone());
        newCourier.setStatus(CourierStatus.AVAILABLE);
        newCourier.setCurrentLocation(courier.getCurrentLocation());

        return repository.save(newCourier);
    }

    @Transactional
    public Courier assignShipment(Long courierId, Long shipmentId) {
        Courier courier = repository.findById(courierId).orElse(null);
        if (courier != null && courier.getStatus() == CourierStatus.AVAILABLE) {
            courier.setAssignedShipmentId(shipmentId);
            courier.setStatus(CourierStatus.ON_DELIVERY);
            return repository.save(courier);
        }
        return null;
    }

    @Transactional
    public Courier updateStatus(Long courierId, String statusString) {
        Courier courier = repository.findById(courierId).orElse(null);
        if (courier != null) {
            try {
                CourierStatus status = CourierStatus.valueOf(statusString.toUpperCase());
                courier.setStatus(status);

                // Eğer AVAILABLE oluyorsa, atanmış shipment'ı temizle
                if (status == CourierStatus.AVAILABLE) {
                    courier.setAssignedShipmentId(null);
                }

                return repository.save(courier);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status: " + statusString);
            }
        }
        return null;
    }

    @Transactional
    public Courier updateLocation(Long courierId, String location) {
        Courier courier = repository.findById(courierId).orElse(null);
        if (courier != null) {
            courier.setCurrentLocation(location);
            return repository.save(courier);
        }
        return null;
    }

    @Transactional
    public void deleteCourier(Long id) {
        repository.deleteById(id);
    }
}