package com.example.ctms.controller;

import com.example.ctms.service.ContainerTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.ctms.entity.ContainerTaskPickup;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/task/pickup")
public class ContTaskController {
    @Autowired
    private ContainerTaskService containerTaskService;
    @GetMapping
    public List<ContainerTaskPickup> getTaskPickup(
            @RequestParam(value = "doNumber") String pickupDoNumber,
            @RequestParam(value = "bookingNumber") String pickupBookingNumber,
            @RequestParam(value = "ctnNumber") String pickupCtnNumber,
            @RequestParam(value = "expDate", required = false) LocalDate pickupExpDate,
            @RequestParam(value = "expCtnDate", required = false) LocalDate pickupExpCtnDate,
            @RequestParam(value = "shipperName") String shipperName,
            @RequestParam(value = "shipperRepresent") String shipperRepresent,
            @RequestParam(value = "shipperPhone") String shipperPhone,
            @RequestParam(value = "truck", required = false) String isTruck
    ) {
        return containerTaskService.getContainerTaskPickups(pickupDoNumber, pickupBookingNumber, pickupCtnNumber,
                pickupExpDate, pickupExpCtnDate, shipperName, shipperRepresent, shipperPhone, isTruck);
    }


}
