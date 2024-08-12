package com.example.ctms.repository;

import com.example.ctms.entity.Customer;
import com.example.ctms.entity.EmptyContainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmptyContainerRepository extends JpaRepository<EmptyContainer, Integer> {

    List<EmptyContainer> findByIsApprovedEquals(int isApproved);

    List<EmptyContainer> findByCustomerIdAndIsApproved(Integer customer_id, int isApproved);
}

// Thêm repository còn thiếu khác nếu cần.
