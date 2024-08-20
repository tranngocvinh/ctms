package com.example.ctms.mapper;

import com.example.ctms.dto.DeliveryOrderDTO;
import com.example.ctms.dto.RepairDTO;
import com.example.ctms.entity.DeliveryOrder;
import com.example.ctms.entity.Repair;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class RepairDTOMapper  implements Function<Repair, RepairDTO> {
    @Override
    public RepairDTO apply(Repair repair) {
        return new RepairDTO(
                repair.getId(),
                repair.getContainer().getContainerCode(),
                repair.getSupplier().getSupplierId(),
                repair.getRepairCost(),
                repair.getRepairDate(),
                repair.getDescription(),
                repair.getIsRepair(),
                repair.getIsPayment()
        );
    }
}
