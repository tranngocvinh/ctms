package com.example.ctms.mapper;

import com.example.ctms.dto.ShipDTO;
import com.example.ctms.dto.ScheduleDTO;
import com.example.ctms.dto.ShipScheduleDTO;
import com.example.ctms.dto.WaypointDTO;
import com.example.ctms.entity.ShipSchedule;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShipScheduleMapper {
    public static ShipScheduleDTO toDTO(ShipSchedule shipSchedule) {
        Long id = shipSchedule.getId();
        ShipDTO shipDTO = new ShipDTO(
                shipSchedule.getShip().getId(),
                shipSchedule.getShip().getName(),
                shipSchedule.getShip().getCompany(),
                shipSchedule.getShip().getCapacity(),
                shipSchedule.getShip().getRegistrationNumber(),
                shipSchedule.getShip().getYearBuilt(),
                shipSchedule.getShip().getStatus()
        );

        ScheduleDTO scheduleDTO = new ScheduleDTO(
                shipSchedule.getSchedule().getId(),
                shipSchedule.getSchedule().getRoute().getId(),
                shipSchedule.getSchedule().getRoute().getName(),
                shipSchedule.getSchedule().getDepartureTime(),
                shipSchedule.getSchedule().getEstimatedArrivalTime(),
                shipSchedule.getSchedule().getActualDepartureTime(),
                shipSchedule.getSchedule().getActualArrivalTime(),
                shipSchedule.getSchedule().getStatus(),
                shipSchedule.getSchedule().getNotes(),
                shipSchedule.getSchedule().getRoute().getWaypoints().stream()
                        .map(waypoint -> new WaypointDTO(
                                waypoint.getPortName(),
                                waypoint.getLat(),
                                waypoint.getLon()
                        ))
                        .collect(Collectors.toList()),
                shipSchedule.getSchedule().getShipSchedules().stream()
                        .map(ss -> ss.getContainer() != null ? ss.getContainer().getContainerCode() : null)
                        .filter(containerCode -> containerCode != null)  // Only collect non-null container codes
                        .collect(Collectors.toList()),
                shipSchedule.getSchedule().getShipSchedules().stream()
                        .map(ss -> ss.getShip().getId())
                        .collect(Collectors.toList())
        );

        return new ShipScheduleDTO(
                id,
                shipDTO,
                scheduleDTO
        );
    }
}
