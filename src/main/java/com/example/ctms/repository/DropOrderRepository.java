package com.example.ctms.repository;

import com.example.ctms.entity.DropOrder;
import com.example.ctms.entity.SI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface DropOrderRepository extends JpaRepository<DropOrder, Integer> {
    Optional<DropOrder> findDropOrderBySiId(Integer id);

    @Query("SELECT SUM(c.detFee) FROM DropOrder c")
    Long sumAllDetFee();

    // Sum detFee by month
    @Query("SELECT MONTH(do.dropDate) AS month, SUM(do.detFee) AS totalDetFee " +
            "FROM DropOrder do " +
            "GROUP BY MONTH(do.dropDate)")
    List<Map<String, Object>> sumDetFeeByMonth();
}
