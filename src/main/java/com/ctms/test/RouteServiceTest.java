package com.ctms.test;


import com.ctms.dto.RouteDTO;
import com.ctms.dto.RouteSegmentDTO;
import com.ctms.dto.WaypointDTO;
import com.ctms.entity.Route;
import com.ctms.entity.RouteSegment;
import com.ctms.entity.Waypoint;
import com.ctms.repository.RouteRepository;
import com.ctms.repository.WaypointRepository;
import com.ctms.repository.RouteSegmentRepository;
import com.ctms.service.RouteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RouteServiceTest {

    @Mock
    private RouteRepository routeRepository;

    @Mock
    private WaypointRepository waypointRepository;

    @Mock
    private RouteSegmentRepository routeSegmentRepository;

    @InjectMocks
    private RouteService routeService;

    private Route route1;
    private Route route2;
    private RouteDTO routeDTO;
    private Waypoint waypoint1;
    private Waypoint waypoint2;
    private RouteSegment routeSegment1;
    private RouteSegment routeSegment2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);


        route1 = new Route("Route A", 1440, 1500.0, "ACTIVE", "Route from Haiphong to Singapore");
        route2 = new Route("Route B", 2880, 3000.0, "ACTIVE", "Route from Singapore to Shanghai");
        waypoint1 = new Waypoint("Port A", 10.0, 20.0, route1);
        waypoint2 = new Waypoint("Port B", 30.0, 40.0, route2);
        waypoint1.setId(1);
        waypoint2.setId(2);

        routeSegment1 = new RouteSegment(waypoint1, waypoint2,route1, 1);
        routeSegment2 = new RouteSegment(waypoint2, waypoint1,route2, 2);

        route1.setId(1);
        route2.setId(2);
        route1.setWaypoints(List.of(waypoint1, waypoint2));
        route2.setWaypoints(List.of(waypoint1, waypoint2));
        route1.setRouteSegments(List.of(routeSegment1));
        route2.setRouteSegments(List.of(routeSegment2));

        WaypointDTO waypointDTO1 = new WaypointDTO("Port A", 10.0, 20.0);
        WaypointDTO waypointDTO2 = new WaypointDTO("Port B", 30.0, 40.0);
        RouteSegmentDTO routeSegmentDTO = new RouteSegmentDTO(1, 2, 1);

        routeDTO = new RouteDTO(1, "Route A", 1440, 1500.0, "ACTIVE", "Route from Haiphong to Singapore", List.of(waypointDTO1, waypointDTO2), List.of(routeSegmentDTO));
    }

    @Test
    public void testGetAllRoutes() {
        // Arrange
        when(routeRepository.findAll()).thenReturn(List.of(route1, route2));

        // Act
        List<RouteDTO> routes = routeService.getAllRoutes();

        // Assert
        assertEquals(2, routes.size());
        verify(routeRepository, times(1)).findAll();
    }

    @Test
    public void testAddRoute() {
        // Arrange
        when(routeRepository.save(any(Route.class))).thenReturn(route1);
        when(waypointRepository.findById(1)).thenReturn(Optional.of(waypoint1));
        when(waypointRepository.findById(2)).thenReturn(Optional.of(waypoint2));

        // Act
        RouteDTO result = routeService.addRoute(routeDTO);

        // Assert
        assertNotNull(result);
        assertEquals(routeDTO.name(), result.name());
        verify(routeRepository, times(1)).save(any(Route.class));
        verify(routeSegmentRepository, times(1)).saveAll(anyList());
    }

//    @Test
//    public void testUpdateRoute() {
//        // Arrange
//        when(routeRepository.findById(1)).thenReturn(Optional.of(route1));
//        when(routeRepository.save(any(Route.class))).thenReturn(route1);
//        when(waypointRepository.findById(1)).thenReturn(Optional.of(waypoint1));
//        when(waypointRepository.findById(2)).thenReturn(Optional.of(waypoint2));
//
//        RouteDTO updatedRouteDTO = new RouteDTO(1, "Updated Route", 1500, 2000.0, "INACTIVE", "Updated Description", routeDTO.waypoints(), routeDTO.routeSegments());
//
//        // Act
//        RouteDTO result = routeService.updateRoute(1, updatedRouteDTO);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals("Updated Route", result.name());
//        verify(routeRepository, times(1)).findById(1);
//        verify(routeRepository, times(1)).save(any(Route.class));
//    }

    @Test
    public void testDeleteRoute() {
        // Act
        routeService.deleteRoute(1);

        // Assert
        verify(routeRepository, times(1)).deleteById(1);
    }
}