package com.example.ctms.repository;

import com.example.ctms.entity.EmptyContainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmptyContainerRepository extends JpaRepository<EmptyContainer, Integer> {
}

// Thêm repository còn thiếu khác nếu cần.
