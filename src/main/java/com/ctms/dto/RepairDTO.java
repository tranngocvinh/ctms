package com.ctms.dto;

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
