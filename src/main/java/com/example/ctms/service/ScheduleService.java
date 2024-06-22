package com.example.ctms.service;

import com.example.ctms.dto.ScheduleDTO;
import com.example.ctms.entity.Route;
import com.example.ctms.entity.Schedule;
import com.example.ctms.repository.RouteRepository;
import com.example.ctms.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final RouteRepository routeRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, RouteRepository routeRepository) {
        this.scheduleRepository = scheduleRepository;
        this.routeRepository = routeRepository;
    }

    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public ScheduleDTO addSchedule(ScheduleDTO scheduleDTO) {
        Route route = routeRepository.findById(scheduleDTO.routeId())
                .orElseThrow(() -> new RuntimeException("Route not found with ID: " + scheduleDTO.routeId()));

        Schedule schedule = new Schedule();
        schedule.setRoute(route);
        schedule.setDepartureTime(scheduleDTO.departureTime());
        schedule.setEstimatedArrivalTime(scheduleDTO.estimatedArrivalTime());
        schedule.setActualDepartureTime(scheduleDTO.actualDepartureTime());
        schedule.setActualArrivalTime(scheduleDTO.actualArrivalTime());
        schedule.setStatus(scheduleDTO.status());
        schedule.setNotes(scheduleDTO.notes());

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return convertToDto(savedSchedule);
    }

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
                    return scheduleRepository.save(schedule);
                })
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + id));
        return convertToDto(updatedSchedule);
    }

    public void deleteSchedule(Integer id) {
        scheduleRepository.deleteById(id);
    }

    private ScheduleDTO convertToDto(Schedule schedule) {
        return new ScheduleDTO(
                schedule.getId(),
                schedule.getRoute().getId(),
                schedule.getRoute().getName(),
                schedule.getDepartureTime(),
                schedule.getEstimatedArrivalTime(),
                schedule.getActualDepartureTime(),
                schedule.getActualArrivalTime(),
                schedule.getStatus(),
                schedule.getNotes()
        );
    }
}
