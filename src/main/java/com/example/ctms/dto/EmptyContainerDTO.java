package com.example.ctms.dto;

import com.example.ctms.entity.Customer;
import com.example.ctms.entity.EmptyContainerDetail;
import com.example.ctms.entity.PortLocation;
import com.example.ctms.entity.Ship;

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
