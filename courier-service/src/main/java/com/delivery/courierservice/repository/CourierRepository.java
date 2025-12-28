package com.delivery.courierservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.delivery.courierservice.model.Courier;
import com.delivery.courierservice.model.CourierStatus;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {

    // Müsait courier'ları bul
    List<Courier> findByStatus(CourierStatus status);

    // İsme göre courier bul
    Courier findByName(String name);
}