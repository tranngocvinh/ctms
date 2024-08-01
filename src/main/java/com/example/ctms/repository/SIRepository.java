package com.example.ctms.repository;

import com.example.ctms.entity.SI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SIRepository extends JpaRepository<SI, Integer> {
}
