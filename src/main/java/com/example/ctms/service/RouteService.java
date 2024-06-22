package com.example.ctms.service;

import com.example.ctms.dto.RouteDTO;
import com.example.ctms.dto.WaypointDTO;
import com.example.ctms.entity.Route;
import com.example.ctms.entity.Waypoint;
import com.example.ctms.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteService {
    @Autowired
    private RouteRepository routeRepository;

    public List<RouteDTO> getAllRoutes() {
        return routeRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public RouteDTO addRoute(RouteDTO routeDTO) {
        Route route = convertToEntity(routeDTO);
        Route savedRoute = routeRepository.save(route);
        return convertToDto(savedRoute);
    }

    public RouteDTO updateRoute(Integer id, RouteDTO routeDTO) {
        Route route = routeRepository.findById(id).orElseThrow(() -> new RuntimeException("Route not found"));
        route.setName(routeDTO.name());
        route.setEstimatedTime(routeDTO.estimatedTime());
        route.setDistance(routeDTO.distance());
        route.setStatus(routeDTO.status());
        route.setDescription(routeDTO.description());

        // Update waypoints
        route.getWaypoints().clear();
        route.getWaypoints().addAll(routeDTO.waypoints().stream().map(dto -> new Waypoint(dto.portName(), dto.lat(), dto.lon(), route)).collect(Collectors.toList()));

        Route updatedRoute = routeRepository.save(route);
        return convertToDto(updatedRoute);
    }

    public void deleteRoute(Integer id) {
        routeRepository.deleteById(id);
    }

    private RouteDTO convertToDto(Route route) {
        List<WaypointDTO> waypointDTOS = route.getWaypoints().stream()
                .map(waypoint -> new WaypointDTO(waypoint.getPortName(), waypoint.getLat(), waypoint.getLon()))
                .collect(Collectors.toList());

        return new RouteDTO(route.getId(), route.getName(), route.getEstimatedTime(), route.getDistance(), route.getStatus(), route.getDescription(), waypointDTOS);
    }

    private Route convertToEntity(RouteDTO routeDTO) {
        Route route = new Route();
        route.setName(routeDTO.name());
        route.setEstimatedTime(routeDTO.estimatedTime());
        route.setDistance(routeDTO.distance());
        route.setStatus(routeDTO.status());
        route.setDescription(routeDTO.description());

        List<Waypoint> waypoints = routeDTO.waypoints().stream()
                .map(dto -> new Waypoint(dto.portName(), dto.lat(), dto.lon(), route))
                .collect(Collectors.toList());

        route.setWaypoints(waypoints);
        return route;
    }
}
