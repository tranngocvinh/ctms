package com.example.ctms.controller;

import com.example.ctms.entity.PortLocation;
import com.example.ctms.service.PortLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ports")
public class PortLocationController {

    private final PortLocationService portLocationService;

    @Autowired
    public PortLocationController(PortLocationService portLocationService) {
        this.portLocationService = portLocationService;
    }

    @GetMapping
    public List<PortLocation> getAllPorts() {
        return portLocationService.getAllPorts();
    }
    @GetMapping("/search")
    public List<PortLocation> searchPorts(@RequestParam String name) {
        return portLocationService.searchPortsByName(name);
    }
}
