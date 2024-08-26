package com.ctms.service;

import com.ctms.dto.RouteSegmentDTO;
import com.ctms.entity.RouteSegment;
import com.ctms.mapper.RouteSegmentDTOMapper;
import com.ctms.repository.RouteSegmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteSegmentService {

    private final RouteSegmentRepository routeSegmentRepository;

    private final RouteSegmentDTOMapper routeSegmentDTOMapper;

    @Autowired
    public RouteSegmentService(RouteSegmentRepository routeSegmentRepository, RouteSegmentDTOMapper routeSegmentDTOMapper) {
        this.routeSegmentRepository = routeSegmentRepository;
        this.routeSegmentDTOMapper = routeSegmentDTOMapper;

    }

    public List<RouteSegmentDTO> getRouteSegmentsByRouteId(Integer routeId) {
        return routeSegmentRepository.findByRouteId(routeId).stream()
                .map(routeSegmentDTOMapper)
                .toList();
    }

    public RouteSegment getRouteSegmentById(Integer id) {
        return routeSegmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RouteSegment not found"));
    }

    public List<RouteSegmentDTO> getRouteSegments() {
        return routeSegmentRepository.findAll().stream()
                .map(routeSegmentDTOMapper)
                .toList();
    }
}
