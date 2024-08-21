package com.example.ctms.controller;

import com.example.ctms.dto.RepairDTO;
import com.example.ctms.entity.Cleaning;
import com.example.ctms.entity.Repair;
import com.example.ctms.service.ContainerCleaningService;
import com.example.ctms.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/clean")
public class ContainerCleaningController {

    @Autowired
    private ContainerCleaningService containerCleaningService;

    @PostMapping
    public Cleaning createCleaningPrice(@RequestBody Cleaning cleaning) {
        return containerCleaningService.createcLeaning(cleaning);
    }


}
