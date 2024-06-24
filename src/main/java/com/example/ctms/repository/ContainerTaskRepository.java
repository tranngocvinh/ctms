package com.example.ctms.repository;

import com.example.ctms.entity.ContainerTaskPickup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ContainerTaskRepository extends JpaRepository<ContainerTaskPickup, Long> {
    @Query("SELECT p FROM ContainerTaskPickup p WHERE " +
            "(p.pickupDoNumber = :pickupDoNumber) AND " +
            "(p.pickupBookingNumber = :pickupBookingNumber) AND " +
            "(p.pickupCtnNumber = :pickupCtnNumber) AND " +
            "(:pickupExpDate is NULL or p.pickupExpDate = :pickupExpDate) AND " +
            "(:pickupExpCtnDate is NULL or p.pickupExpCtnDate = :pickupExpCtnDate) AND " +
            "(p.shipperName = :shipperName) AND " +
            "(p.shipperRepresent = :shipperRepresent) AND " +
            "(p.shipperPhone = :shipperPhone) AND " +
            "(:isTruck is Null or p.isTruck = :isTruck)")
    List<ContainerTaskPickup> findPickupTask(String pickupDoNumber, String pickupBookingNumber,
                                                  String pickupCtnNumber, LocalDate pickupExpDate,
                                                  LocalDate pickupExpCtnDate, String shipperName,
                                                  String shipperRepresent, String shipperPhone,
                                                  String isTruck);

}
