package com.example.ctms.dto;

import java.time.LocalDateTime;

public record ContainerSizeDTO(
        Integer id,
        Double length,
        Double width,
        Double height,
        Double volume,
        Double weight,
        Double loadCapacity,
        Double maxLoad,
        Integer containerTypeId,
        String containerTypeName,
        String containerTypeType,
        Integer shipId,
        String shipName,
        Integer scheduleId,
        String scheduleStatus,
        LocalDateTime departureTime
) {}
