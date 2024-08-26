package com.ctms.dto;

import com.ctms.entity.Customer;
import com.ctms.entity.PortLocation;

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
        ShipScheduleDTO shipSchedule,
        Customer customer,
        int isApprove,
        LocalDateTime localDateTime,
        int isRepair,
        EmptyContainerDetailDTO emptyContainerDetail
) {}