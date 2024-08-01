package com.example.ctms.service;

import com.example.ctms.dto.ScheduleDTO;
import com.example.ctms.dto.ScheduleSegmentDTO;
import com.example.ctms.dto.WaypointDTO;
import com.example.ctms.entity.*;
import com.example.ctms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final RouteRepository routeRepository;
    private final WaypointRepository waypointRepository;
    private final ContainerRepository containerRepository;
    private final ShipScheduleRepository shipScheduleRepository;
    private final ShipRepository shipRepository;
    private final RouteSegmentRepository routeSegmentRepository;
    private final ScheduleSegmentRepository scheduleSegmentRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, RouteRepository routeRepository, WaypointRepository waypointRepository, ContainerRepository containerRepository, ShipScheduleRepository shipScheduleRepository, ShipRepository shipRepository, RouteSegmentRepository routeSegmentRepository, ScheduleSegmentRepository scheduleSegmentRepository) {
        this.scheduleRepository = scheduleRepository;
        this.routeRepository = routeRepository;
        this.waypointRepository = waypointRepository;
        this.containerRepository = containerRepository;
        this.shipScheduleRepository = shipScheduleRepository;
        this.shipRepository = shipRepository;
        this.routeSegmentRepository = routeSegmentRepository;
        this.scheduleSegmentRepository = scheduleSegmentRepository;
    }

    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public ScheduleDTO getScheduleById(Integer id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id " + id));
        return convertToDto(schedule);
    }

    @Transactional
    public ScheduleDTO addSchedule(ScheduleDTO scheduleDTO) {
        Route route = routeRepository.findById(scheduleDTO.routeId())
                .orElseThrow(() -> new RuntimeException("Route not found with ID: " + scheduleDTO.routeId()));
        Schedule schedule = new Schedule(
                route,
                scheduleDTO.departureTime(),
                scheduleDTO.estimatedArrivalTime(),
                scheduleDTO.actualDepartureTime(),
                scheduleDTO.actualArrivalTime(),
                scheduleDTO.status(),
                scheduleDTO.notes()
        );

        Schedule savedSchedule = scheduleRepository.save(schedule);

        // Create ShipSchedule entries without container
        scheduleDTO.ships().forEach(shipId -> {
            Ship ship = shipRepository.findById(shipId)
                    .orElseThrow(() -> new RuntimeException("Ship not found with ID: " + shipId));
            ShipSchedule newShipSchedule = new ShipSchedule(null, ship, savedSchedule);
            shipScheduleRepository.save(newShipSchedule);
        });

        // Create ScheduleSegment entries
        scheduleDTO.scheduleSegments().forEach(segmentDTO -> {
            RouteSegment routeSegment = routeSegmentRepository.findById(segmentDTO.routeSegmentId())
                    .orElseThrow(() -> new RuntimeException("RouteSegment not found with ID: " + segmentDTO.routeSegmentId()));
            ScheduleSegment scheduleSegment = new ScheduleSegment(savedSchedule, routeSegment, segmentDTO.departureTime(), segmentDTO.arrivalTime());
            scheduleSegmentRepository.save(scheduleSegment);
        });

        return convertToDto(savedSchedule);
    }

    @Transactional
    public ScheduleDTO updateSchedule(Integer id, ScheduleDTO scheduleDTO) {
        Route route = routeRepository.findById(scheduleDTO.routeId())
                .orElseThrow(() -> new RuntimeException("Route not found with ID: " + scheduleDTO.routeId()));

        Schedule updatedSchedule = scheduleRepository.findById(id)
                .map(schedule -> {
                    schedule.setRoute(route);
                    schedule.setDepartureTime(scheduleDTO.departureTime());
                    schedule.setEstimatedArrivalTime(scheduleDTO.estimatedArrivalTime());
                    schedule.setActualDepartureTime(scheduleDTO.actualDepartureTime());
                    schedule.setActualArrivalTime(scheduleDTO.actualArrivalTime());
                    schedule.setStatus(scheduleDTO.status());
                    schedule.setNotes(scheduleDTO.notes());

                    // Update ShipSchedule entries
                    shipScheduleRepository.deleteByScheduleId(schedule.getId());
                    scheduleDTO.ships().forEach(shipId -> {
                        Ship ship = shipRepository.findById(shipId)
                                .orElseThrow(() -> new RuntimeException("Ship not found with ID: " + shipId));
                        ShipSchedule shipSchedule = new ShipSchedule(null, ship, schedule);
                        shipScheduleRepository.save(shipSchedule);
                    });

                    // Update ScheduleSegment entries
                    scheduleSegmentRepository.deleteByScheduleId(schedule.getId());
                    scheduleDTO.scheduleSegments().forEach(segmentDTO -> {
                        RouteSegment routeSegment = routeSegmentRepository.findById(segmentDTO.routeSegmentId())
                                .orElseThrow(() -> new RuntimeException("RouteSegment not found with ID: " + segmentDTO.routeSegmentId()));
                        ScheduleSegment scheduleSegment = new ScheduleSegment(schedule, routeSegment, segmentDTO.departureTime(), segmentDTO.arrivalTime());
                        scheduleSegmentRepository.save(scheduleSegment);
                    });

                    return scheduleRepository.save(schedule);
                })
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + id));

        return convertToDto(updatedSchedule);
    }

    public void deleteSchedule(Integer id) {
        shipScheduleRepository.deleteByScheduleId(id);
        scheduleSegmentRepository.deleteByScheduleId(id);
        scheduleRepository.deleteById(id);
    }

    private ScheduleDTO convertToDto(Schedule schedule) {
        List<WaypointDTO> waypoints = waypointRepository.findByRoute(schedule.getRoute()).stream()
                .map(this::convertToWaypointDto)
                .collect(Collectors.toList());

        List<String> containerCodes = containerRepository.findByScheduleId(schedule.getId()).stream()
                .map(Container::getContainerCode)
                .collect(Collectors.toList());

        List<Integer> shipIds = shipScheduleRepository.findByScheduleId(schedule.getId()).stream()
                .map(shipSchedule -> shipSchedule.getShip().getId())
                .collect(Collectors.toList());

        List<ScheduleSegmentDTO> scheduleSegments = scheduleSegmentRepository.findBySchedule(schedule).stream()
                .map(segment -> new ScheduleSegmentDTO(segment.getRouteSegment().getId(), segment.getDepartureTime(), segment.getArrivalTime()))
                .collect(Collectors.toList());

        return new ScheduleDTO(
                schedule.getId(),
                schedule.getRoute().getId(),
                schedule.getRoute().getName(),
                schedule.getDepartureTime(),
                schedule.getEstimatedArrivalTime(),
                schedule.getActualDepartureTime(),
                schedule.getActualArrivalTime(),
                schedule.getStatus(),
                schedule.getNotes(),
                waypoints,
                containerCodes,
                shipIds,
                scheduleSegments
        );
    }

    private WaypointDTO convertToWaypointDto(Waypoint waypoint) {
        return new WaypointDTO(
                waypoint.getPortName(),
                waypoint.getLat(),
                waypoint.getLon()
        );
    }
}