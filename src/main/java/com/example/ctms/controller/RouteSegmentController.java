package com.example.ctms.controller;

import com.example.ctms.dto.RouteSegment1DTO;
import com.example.ctms.dto.RouteSegmentDTO;
import com.example.ctms.entity.RouteSegment;
import com.example.ctms.service.RouteSegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/route-segments")
public class RouteSegmentController {

    private final RouteSegmentService routeSegmentService;

    @Autowired
    public RouteSegmentController(RouteSegmentService routeSegmentService) {
        this.routeSegmentService = routeSegmentService;
    }

    @GetMapping
    public List<RouteSegmentDTO> getAllRouteSegments() {
        return routeSegmentService.getRouteSegments();
    }

    @GetMapping("/{id}")
    public RouteSegment getRouteSegmentById(@PathVariable Integer id) {
        return routeSegmentService.getRouteSegmentById(id);
    }

    @GetMapping("/route/{routeId}")
    public List<RouteSegmentDTO> getRouteSegmentsByRouteId(@PathVariable Integer routeId) {
        return routeSegmentService.getRouteSegmentsByRouteId(routeId);
    }
}
