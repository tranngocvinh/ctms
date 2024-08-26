package com.ctms.controller;

import com.ctms.entity.Cleaning;
import com.ctms.service.ContainerCleaningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
