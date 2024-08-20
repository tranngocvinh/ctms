package com.example.ctms.repository;

import com.example.ctms.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);
    @Query("SELECT COUNT(c) FROM Customer c")
    Double getTotalUser();
}
