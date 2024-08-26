package com.ctms.dto;

import com.ctms.entity.Customer;
import com.ctms.entity.PortLocation;
import com.ctms.entity.Ship;

import java.time.LocalDateTime;
import java.util.List;

public record EmptyContainerDTO(
        int id,
        LocalDateTime requestTime,
        PortLocation portLocation,
        Ship ship,
        int isApproved,
        List<EmptyContainerDetailDTO> details,
        Customer customer,
        boolean si

) {

}
