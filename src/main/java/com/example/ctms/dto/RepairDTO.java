package com.example.ctms.dto;

import com.example.ctms.entity.Container;
import com.example.ctms.entity.ContainerSupplier;

import java.time.LocalDate;

public record RepairDTO (
        Long id,
        String containerCode,
        Integer containerSupplierId,
        Double repairCode,
        LocalDate repairDate,
        String description,
        int isRepair,
        int isPayment
){
}
