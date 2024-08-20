package com.example.ctms.repository;

import com.example.ctms.dto.DeliveryOrderDTO;
import com.example.ctms.entity.DeliveryOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryOrderRepository  extends JpaRepository<DeliveryOrder, Integer> {

    List<DeliveryOrder> findByCustomerId(Integer id);
}
