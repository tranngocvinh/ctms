package com.ctms.repository;

import com.ctms.entity.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShipRepository extends JpaRepository<Ship, Integer> {

    @Query("SELECT COUNT(c) FROM Ship c")

    long countAllShips();
}
