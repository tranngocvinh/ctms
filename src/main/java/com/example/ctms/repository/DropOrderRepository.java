package com.example.ctms.repository;

import com.example.ctms.entity.DropOrder;
import com.example.ctms.entity.SI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DropOrderRepository extends JpaRepository<DropOrder, Integer> {
    Optional<DropOrder> findDropOrderBySiId(Integer id);

    @Query("SELECT SUM(c.detFee) FROM DropOrder c")
    Long sumAllDetFee();
}
