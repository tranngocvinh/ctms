package com.example.ctms.dto;

import java.time.LocalDateTime;
import java.util.List;

public record DeliveryOrderDTO(
        Integer id,
        String orderNumber,
        LocalDateTime orderDate,
        LocalDateTime deliveryDate,
        Double totalAmount,
        String status,
        String notes,
        Integer customerId, // Thay vì đối tượng Customer, chỉ sử dụng ID
        Integer scheduleId, // Thay vì đối tượng Schedule, chỉ sử dụng ID
        List<String> containerCode
)
{// Getters và Setters
}
