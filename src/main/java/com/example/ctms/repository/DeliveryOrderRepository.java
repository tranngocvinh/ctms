package com.example.ctms.repository;

import com.example.ctms.entity.DeliveryOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryOrderRepository  extends JpaRepository<DeliveryOrder, Integer> {

}
