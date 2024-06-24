package com.example.ctms.service;

import com.example.ctms.entity.ContainerTaskPickup;

import java.time.LocalDate;
import java.util.List;
import com.example.ctms.repository.ContainerTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContainerTaskService {
    @Autowired
    private ContainerTaskRepository containerTaskRepository;
    public List<ContainerTaskPickup> getContainerTaskPickups(String doNumber, String bookingNumber,
                                                             String ctnNumber, LocalDate expDate, LocalDate expCtnDate, String shipperName,
                                                             String shipperRepresent, String shipperPhone, String isTruck) {

        return containerTaskRepository.findPickupTask(doNumber, bookingNumber, ctnNumber, expDate, expCtnDate,
                shipperName, shipperRepresent, shipperPhone, isTruck);
    }

}
