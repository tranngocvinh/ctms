package com.example.ctms.mapper;

import com.example.ctms.dto.*;
import com.example.ctms.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContainerMapper {

    ContainerMapper INSTANCE = Mappers.getMapper(ContainerMapper.class);

    @Mapping(source = "containerSize", target = "containerSize")
    @Mapping(source = "portLocation", target = "portLocation")
    @Mapping(source = "containerSupplier", target = "containerSupplier")
    @Mapping(source = "history", target = "history")
    @Mapping(source = "shipSchedules", target = "shipSchedules")
    @Mapping(source = "customer",target = "customer")
    @Mapping(source = "isApprove",target = "isApprove")
    @Mapping(source = "localDateTime",target = "localDateTime")
    ContainerDTO toDTO(Container container);

    @Mapping(source = "containerSize", target = "containerSize")
    @Mapping(source = "portLocation", target = "portLocation")
    @Mapping(source = "containerSupplier", target = "containerSupplier")
    @Mapping(source = "history", target = "history")
    @Mapping(source = "shipSchedules", target = "shipSchedules")
    Container toEntity(ContainerDTO containerDTO);

    @Mapping(source = "containerType", target = "containerType")
    ContainerSizeDTO toContainerSizeDTO(ContainerSize containerSize);

    @Mapping(target = "containerType", source = "containerType")
    ContainerSize toContainerSize(ContainerSizeDTO containerSizeDTO);

    ContainerSupplierDTO toContainerSupplierDTO(ContainerSupplier containerSupplier);

    ContainerSupplier toContainerSupplier(ContainerSupplierDTO containerSupplierDTO);

    ContainerHistoryDTO toContainerHistoryDTO(ContainerHistory containerHistory);

    ContainerHistory toContainerHistory(ContainerHistoryDTO containerHistoryDTO);

    @Mapping(source = "id", target = "id")
    ShipScheduleDTO toShipScheduleDTO(ShipSchedule shipSchedule);

    @Mapping(source = "id", target = "id")
    ShipSchedule toShipSchedule(ShipScheduleDTO shipScheduleDTO);

    ShipDTO toShipDTO(Ship ship);

    Ship toShip(ShipDTO shipDTO);

    ScheduleDTO toScheduleDTO(Schedule schedule);

    Schedule toSchedule(ScheduleDTO scheduleDTO);

    WaypointDTO toWaypointDTO(Waypoint waypoint);

    Waypoint toWaypoint(WaypointDTO waypointDTO);


}
