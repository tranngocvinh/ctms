package com.example.ctms.repository;

import com.example.ctms.entity.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairRepository extends JpaRepository<Repair, Long> {
    Repair findByContainerContainerCode(String containerContainerCode);

    List<Repair> findByContainerContainerCodeIn(List<String> userContainers);

    @Query("SELECT SUM(r.repairCost) FROM Repair r WHERE r.isPayment = 1")
    Double sumPaidRepairCost();
}
