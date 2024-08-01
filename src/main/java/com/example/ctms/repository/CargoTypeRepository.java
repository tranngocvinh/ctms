package com.example.ctms.repository;

import com.example.ctms.entity.CargoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoTypeRepository extends JpaRepository<CargoType, Integer> {
}
