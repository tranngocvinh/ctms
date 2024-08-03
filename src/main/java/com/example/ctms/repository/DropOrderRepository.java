package com.example.ctms.repository;

import com.example.ctms.entity.DropOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DropOrderRepository extends JpaRepository<DropOrder, Integer> {
}
