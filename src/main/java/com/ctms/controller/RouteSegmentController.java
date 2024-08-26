package com.ctms.controller;

import com.ctms.dto.RouteSegmentDTO;
import com.ctms.entity.RouteSegment;
import com.ctms.service.RouteSegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
