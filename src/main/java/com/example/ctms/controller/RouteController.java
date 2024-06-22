package com.example.ctms.controller;

import com.example.ctms.dto.RouteDTO;
import com.example.ctms.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteController {
    @Autowired
    private RouteService routeService;

    @GetMapping
    public List<RouteDTO> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @PostMapping
    public RouteDTO addRoute(@RequestBody RouteDTO routeDTO) {
        return routeService.addRoute(routeDTO);
    }

    @PutMapping("/{id}")
    public RouteDTO updateRoute(@PathVariable Integer id, @RequestBody RouteDTO routeDTO) {
        return routeService.updateRoute(id, routeDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteRoute(@PathVariable Integer id) {
        routeService.deleteRoute(id);
    }
}
