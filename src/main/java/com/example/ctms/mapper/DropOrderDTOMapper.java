package com.example.ctms.mapper;

import com.example.ctms.dto.DropOrderDTO;
import com.example.ctms.entity.DropOrder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class DropOrderDTOMapper implements Function<DropOrder, DropOrderDTO> {
    @Override
    public DropOrderDTO apply(DropOrder dropOrder) {
        return new DropOrderDTO(
                dropOrder.getId(),
                dropOrder.getSi().getId(),
                dropOrder.getDropDate(),
                dropOrder.getDropLocation(),
                dropOrder.getStatus(),
                dropOrder.getDetFee(),
                dropOrder.getIsPay()
        );
    }
}
