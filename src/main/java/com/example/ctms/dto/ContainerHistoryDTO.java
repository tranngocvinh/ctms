package com.example.ctms.dto;

import java.time.LocalDateTime;

public record ContainerHistoryDTO(
        Integer id,
        LocalDateTime timestamp,
        String status,
        String location,
        Integer shipId,
        String shipName,
        Integer scheduleId,
        String routeName
) {}
