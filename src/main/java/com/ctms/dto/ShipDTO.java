package com.ctms.dto;

public record ShipDTO(
        Integer id,
        String name,
        String company,
        Double capacity,
        String registrationNumber,
        Integer yearBuilt,
        String status
) {}
