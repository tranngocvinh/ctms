package com.example.ctms.mapper;

import com.example.ctms.dto.CustomerDTO;
import com.example.ctms.dto.DeliveryOrderDTO;
import com.example.ctms.entity.Container;
import com.example.ctms.entity.Customer;
import com.example.ctms.entity.DeliveryOrder;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DeliveryOrderDTOMapper implements Function<DeliveryOrder, DeliveryOrderDTO> {
    @Override
    public DeliveryOrderDTO apply(DeliveryOrder deliveryOrder) {
        return new DeliveryOrderDTO(
                deliveryOrder.getId(),
                deliveryOrder.getOrderNumber(),
                deliveryOrder.getOrderDate(),
                deliveryOrder.getDeliveryDate(),
                deliveryOrder.getTotalAmount(),
                deliveryOrder.getStatus(),
                deliveryOrder.getNotes(),
                deliveryOrder.getCustomer().getId(),
                deliveryOrder.getSchedule().getId(),
                deliveryOrder.getContainerCode().stream().map(Container::getContainerCode).collect(Collectors.toList())
        );
    }
}
