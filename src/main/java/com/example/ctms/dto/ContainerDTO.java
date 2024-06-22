package com.example.ctms.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ContainerDTO(
        Integer id,
        Integer containerSizeId,
        String containerSizeDetails,
        Double length,
        Double width,
        Double height,
        Double volume,
        Double weight,
        Double loadCapacity,
        Double maxLoad,
        String status,
        Integer shipId,
        String shipName,
        Integer scheduleId,
        String routeName,
        LocalDateTime departureTime,
        LocalDateTime estimatedArrivalTime,
        String location,
        List<ContainerHistoryDTO> history
) {}
