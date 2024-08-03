package com.example.ctms.dto;

import java.time.LocalDateTime;

public record DropOrderDTO (
        Integer si,
        LocalDateTime dropDate,
        String dropLocation,
        String status
){
}
