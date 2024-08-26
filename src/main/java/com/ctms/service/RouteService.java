package com.ctms.service;

import com.ctms.dto.RouteDTO;
import com.ctms.dto.RouteSegmentDTO;
import com.ctms.dto.WaypointDTO;
import com.ctms.entity.Route;
import com.ctms.entity.RouteSegment;
import com.ctms.entity.Waypoint;
import com.ctms.repository.RouteRepository;
import com.ctms.repository.RouteSegmentRepository;
import com.ctms.repository.WaypointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteService {
    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private WaypointRepository waypointRepository;

    @Autowired
    private RouteSegmentRepository routeSegmentRepository;

    public List<RouteDTO> getAllRoutes() {
        return routeRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public RouteDTO addRoute(RouteDTO routeDTO) {
        Route route = convertToEntity(routeDTO);
        Route savedRoute = routeRepository.save(route);

        Waypoint waypoints = new Waypoint();
        for(int i = 0 ; i< routeDTO.waypoints().size()  ; i++) {
            waypoints.setPortName(routeDTO.waypoints().get(i).portName());
            waypoints.setLat(routeDTO.waypoints().get(i).lat());
            waypoints.setLon(routeDTO.waypoints().get(i).lon());
            waypoints.setRoute(savedRoute);
            waypointRepository.save(waypoints);
        }


        List<RouteSegment> routeSegments = new ArrayList<>();
        for (int i = 0; i < routeDTO.waypoints().size() - 1; i++) {
            RouteSegment routeSegment = new RouteSegment();
            routeSegment.setStartWaypoint(route.getWaypoints().get(i));
            routeSegment.setEndWaypoint(route.getWaypoints().get(i + 1));
            routeSegment.setRoute(savedRoute);
            routeSegment.setSegmentOrder(i + 1);
            routeSegments.add(routeSegment);
        }

        routeSegmentRepository.saveAll(routeSegments);
        savedRoute.setRouteSegments(routeSegments);

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

        // Update route segments
        route.getRouteSegments().clear();
        List<RouteSegment> routeSegments = routeDTO.routeSegments().stream()
                .map(segmentDTO -> {
                    RouteSegment routeSegment = new RouteSegment();
                    Waypoint startWaypoint = waypointRepository.findById(segmentDTO.startWaypointId())
                            .orElseThrow(() -> new RuntimeException("Start waypoint not found"));
                    Waypoint endWaypoint = waypointRepository.findById(segmentDTO.endWaypointId())
                            .orElseThrow(() -> new RuntimeException("End waypoint not found"));
                    routeSegment.setStartWaypoint(startWaypoint);
                    routeSegment.setEndWaypoint(endWaypoint);
                    routeSegment.setRoute(route);
                    routeSegment.setSegmentOrder(segmentDTO.segmentOrder());
                    return routeSegment;
                })
                .collect(Collectors.toList());

        route.getRouteSegments().addAll(routeSegments);

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

        List<RouteSegmentDTO> routeSegmentDTOS = route.getRouteSegments().stream()
                .map(segment -> new RouteSegmentDTO(segment.getId(),segment.getStartWaypoint().getId(), segment.getEndWaypoint().getId(), segment.getSegmentOrder()))
                .collect(Collectors.toList());

        return new RouteDTO(route.getId(), route.getName(), route.getEstimatedTime(), route.getDistance(), route.getStatus(), route.getDescription(), waypointDTOS, routeSegmentDTOS);
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
