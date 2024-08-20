package com.example.ctms.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record DeliveryOrderDTO(
        Integer id,
        String orderNumber,
        LocalDateTime orderDate,
        LocalDateTime deliveryDate,
        Double totalAmount,
        String status,
        String notes,
        int isPay,
        Integer customerId, // Thay vì đối tượng Customer, chỉ sử dụng ID
        Integer scheduleId, // Thay vì đối tượng Schedule, chỉ sử dụng ID
        Map<Long, List<String>> shipScheduleContainerMap // New field for mapping shipScheduleId to container codes
)
{// Getters và Setters
}
