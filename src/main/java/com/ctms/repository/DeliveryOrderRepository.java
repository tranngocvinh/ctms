package com.ctms.repository;

import com.ctms.entity.DeliveryOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface DeliveryOrderRepository  extends JpaRepository<DeliveryOrder, Integer> {

    List<DeliveryOrder> findByCustomerId(Integer id);
    @Query("SELECT SUM(r.totalAmount) FROM DeliveryOrder r WHERE r.isPay = 2")
    Double sumPaiDeliveryCost();

    @Query("SELECT MONTH(do.deliveryDate) AS month, SUM(do.totalAmount) AS totalAmount " +
            "FROM DeliveryOrder do " +
            "GROUP BY MONTH(do.deliveryDate)")
    List<Map<String, Object>> sumTotalAmountByMonth();}
