package com.ctms.repository;

import com.ctms.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT COUNT(c) FROM Customer c")
    Double getTotalUser();

    @Query("SELECT c FROM Customer c WHERE c.email = ?1 ")
    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByResetPasswordToken(String token);

}
