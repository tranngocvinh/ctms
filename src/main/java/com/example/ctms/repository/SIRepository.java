package com.example.ctms.repository;

import com.example.ctms.entity.SI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SIRepository extends JpaRepository<SI, Integer> {
    Optional<SI> findByEmptyContainerId(int id);
}
