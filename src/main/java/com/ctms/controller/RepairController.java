package com.ctms.controller;

import com.ctms.dto.RepairDTO;
import com.ctms.entity.Repair;
import com.ctms.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/repair")
public class RepairController {

    @Autowired
    private RepairService repairService;

    @PostMapping
    public Repair createRepair(@RequestBody Repair repairData) {
        return repairService.createRepair(repairData);
    }

    @GetMapping
    public List<RepairDTO> getAllRepair() {
        return repairService.getAllRepair();
    }

    @GetMapping("/{id}")
    public Optional<RepairDTO> getRepairById(@PathVariable Long id) {
        return repairService.getRepairById(id);
    }

    @PutMapping("/{id}")
    public void updateRepair(@PathVariable Long id, @RequestBody RepairDTO repairDTO) {
         repairService.updateRepair(id, repairDTO);
    }

    @PutMapping("finish/{id}")
    public void repairFinish(@PathVariable Long id) {
        repairService.repairFinish(id);
    }


    @PutMapping("payment/{id}")
    public void handlePayment(@PathVariable Long id) {
        repairService.handlePayment(id);
    }

    @GetMapping("/cost/paid")
    public Double getTotalPaidRepairCost() {
        return repairService.getTotalPaidRepairCost();
    }

    @GetMapping("/repaircost-count")
    public Map<Integer, Double> getRepairCostCountByMonth() {
        return repairService.getRepairCostCountByMonth();
    }
}
