package com.example.ctms.dto;

import com.example.ctms.entity.PortLocation;

import java.time.LocalDateTime;
import java.util.List;

public record ContainerDTO(
        String containerCode,
        ContainerSizeDTO containerSize,
        String status,
        PortLocation portLocation,
        ContainerSupplierDTO containerSupplier,
        boolean hasGoods,
        List<ContainerHistoryDTO> history,
        List<ShipScheduleDTO> shipSchedules
) {}