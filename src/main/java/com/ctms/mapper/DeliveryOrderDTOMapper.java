package com.ctms.mapper;

import com.ctms.dto.DeliveryOrderDTO;
import com.ctms.entity.Container;
import com.ctms.entity.DeliveryOrder;
import com.ctms.entity.ShipSchedule;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DeliveryOrderDTOMapper implements Function<DeliveryOrder, DeliveryOrderDTO> {
    @Override
    public DeliveryOrderDTO apply(DeliveryOrder deliveryOrder) {
        // Creating the shipScheduleContainerMap
        Map<Long, List<String>> shipScheduleContainerMap = new HashMap<>();

        // Iterate over the ship schedules and populate the map
        for (ShipSchedule shipSchedule : deliveryOrder.getSchedule().getShipSchedules()) {
            List<String> containerCodes = shipSchedule.getContainers().stream()
                    .map(Container::getContainerCode)
                    .collect(Collectors.toList());

            shipScheduleContainerMap.put(shipSchedule.getId(), containerCodes);
        }

        return new DeliveryOrderDTO(
                deliveryOrder.getId(),
                deliveryOrder.getOrderNumber(),
                deliveryOrder.getOrderDate(),
                deliveryOrder.getDeliveryDate(),
                deliveryOrder.getTotalAmount(),
                deliveryOrder.getStatus(),
                deliveryOrder.getNotes(),
                deliveryOrder.getIsPay(),
                deliveryOrder.getCustomer().getId(),
                deliveryOrder.getSchedule().getId(),
                shipScheduleContainerMap  // Add the new field here
        );
    }
}

