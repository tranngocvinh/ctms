package com.ctms.test;


import com.ctms.dto.ScheduleDTO;
import com.ctms.dto.ScheduleSegmentDTO;
import com.ctms.dto.WaypointDTO;
import com.ctms.entity.*;
import com.ctms.repository.*;
import com.ctms.service.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private RouteRepository routeRepository;

    @Mock
    private WaypointRepository waypointRepository;

    @Mock
    private ContainerRepository containerRepository;

    @Mock
    private ShipScheduleRepository shipScheduleRepository;

    @Mock
    private ShipRepository shipRepository;

    @Mock
    private RouteSegmentRepository routeSegmentRepository;

    @Mock
    private ScheduleSegmentRepository scheduleSegmentRepository;

    @InjectMocks
    private ScheduleService scheduleService;

    private Route route;
    private Schedule schedule;
    private ScheduleDTO scheduleDTO;
    private Ship ship;
    private RouteSegment routeSegment;
    private ScheduleSegment scheduleSegment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        route = new Route("Route A", 1440, 1500.0, "ACTIVE", "Route from Haiphong to Singapore");
        route.setId(1);

        ship = new Ship();
        ship.setId(1);

        routeSegment = new RouteSegment();
        routeSegment.setId(1);

        schedule = new Schedule(route, LocalDateTime.now(), LocalDateTime.now().plusDays(1), LocalDateTime.now(), LocalDateTime.now().plusDays(1), "SCHEDULED", "Notes");
        schedule.setId(1);

        scheduleSegment = new ScheduleSegment(schedule, routeSegment, LocalDateTime.now(), LocalDateTime.now().plusHours(2));
        scheduleSegment.setId(1);

        WaypointDTO waypointDTO = new WaypointDTO("Port A", 10.0, 20.0);
        ScheduleSegmentDTO scheduleSegmentDTO = new ScheduleSegmentDTO(routeSegment.getId(), LocalDateTime.now(), LocalDateTime.now().plusHours(2));

        scheduleDTO = new ScheduleDTO(
                1,
                route.getId(),
                route.getName(),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                "SCHEDULED",
                "Notes",
                List.of(waypointDTO),
                List.of("CONT1"),
                List.of(ship.getId()),
                List.of(scheduleSegmentDTO)
        );
    }

    @Test
    void testGetAllSchedules() {
        when(scheduleRepository.findAll()).thenReturn(List.of(schedule));

        List<ScheduleDTO> schedules = scheduleService.getAllSchedules();

        assertEquals(1, schedules.size());
        verify(scheduleRepository, times(1)).findAll();
    }

    @Test
    void testGetScheduleById() {
        when(scheduleRepository.findById(1)).thenReturn(Optional.of(schedule));

        ScheduleDTO result = scheduleService.getScheduleById(1);

        assertNotNull(result);
        assertEquals(schedule.getId(), result.id());
        verify(scheduleRepository, times(1)).findById(1);
    }

    @Test
    @Transactional
    void testAddSchedule() {
        when(routeRepository.findById(route.getId())).thenReturn(Optional.of(route));
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);
        when(shipRepository.findById(ship.getId())).thenReturn(Optional.of(ship));
        when(routeSegmentRepository.findById(routeSegment.getId())).thenReturn(Optional.of(routeSegment));

        ScheduleDTO result = scheduleService.addSchedule(scheduleDTO);

        assertNotNull(result);
        assertEquals(scheduleDTO.routeId(), result.routeId());
        verify(scheduleRepository, times(1)).save(any(Schedule.class));
        verify(shipScheduleRepository, times(1)).save(any(ShipSchedule.class));
        verify(scheduleSegmentRepository, times(1)).save(any(ScheduleSegment.class));
    }

    @Test
    @Transactional
    void testUpdateSchedule() {
        when(routeRepository.findById(route.getId())).thenReturn(Optional.of(route));
        when(scheduleRepository.findById(schedule.getId())).thenReturn(Optional.of(schedule));
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);
        when(shipRepository.findById(ship.getId())).thenReturn(Optional.of(ship));
        when(routeSegmentRepository.findById(routeSegment.getId())).thenReturn(Optional.of(routeSegment));

        ScheduleDTO updatedScheduleDTO = new ScheduleDTO(
                1,
                route.getId(),
                route.getName(),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(2),
                "UPDATED",
                "Updated Notes",
                scheduleDTO.waypoints(),
                scheduleDTO.containerCodes(),
                scheduleDTO.ships(),
                scheduleDTO.scheduleSegments()
        );

        ScheduleDTO result = scheduleService.updateSchedule(1, updatedScheduleDTO);

        assertNotNull(result);
        assertEquals("UPDATED", result.status());
        verify(scheduleRepository, times(1)).findById(1);
        verify(scheduleRepository, times(1)).save(any(Schedule.class));
        verify(shipScheduleRepository, times(1)).deleteByScheduleId(1);
        verify(scheduleSegmentRepository, times(1)).deleteByScheduleId(1);
    }

    @Test
    void testDeleteSchedule() {
        doNothing().when(shipScheduleRepository).deleteByScheduleId(1);
        doNothing().when(scheduleSegmentRepository).deleteByScheduleId(1);
        doNothing().when(scheduleRepository).deleteById(1);

        scheduleService.deleteSchedule(1);

        verify(shipScheduleRepository, times(1)).deleteByScheduleId(1);
        verify(scheduleSegmentRepository, times(1)).deleteByScheduleId(1);
        verify(scheduleRepository, times(1)).deleteById(1);
    }
}