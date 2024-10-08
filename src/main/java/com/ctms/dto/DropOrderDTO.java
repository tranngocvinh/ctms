package com.ctms.dto;

import java.time.LocalDateTime;

public record DropOrderDTO (
        Integer id,
        Integer si,
        LocalDateTime dropDate,
        String dropLocation,
        String status,
        Double detFee,
        Integer isPay
){
}
