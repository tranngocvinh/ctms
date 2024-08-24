package com.example.ctms.mapper;

import com.example.ctms.dto.*;
import com.example.ctms.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContainerMapper {

    ContainerMapper INSTANCE = Mappers.getMapper(ContainerMapper.class);
    @Mapping(source = "emptyContainerDetail", target = "emptyContainerDetail")
    @Mapping(source = "containerSize", target = "containerSize")
    @Mapping(source = "portLocation", target = "portLocation")
    @Mapping(source = "containerSupplier", target = "containerSupplier")
    @Mapping(source = "history", target = "history")
    @Mapping(source = "customer",target = "customer")
    @Mapping(source = "isApprove",target = "isApprove")
    @Mapping(source = "isRepair",target = "isRepair")
    @Mapping(source = "localDateTime",target = "localDateTime")
    @Mapping(source ="shipSchedule" , target = "shipSchedule")
    ContainerDTO toDTO(Container container);

    @Mapping(source = "containerSize", target = "containerSize")
    @Mapping(source = "portLocation", target = "portLocation")
    @Mapping(source = "containerSupplier", target = "containerSupplier")
    @Mapping(source = "history", target = "history")
    Container toEntity(ContainerDTO containerDTO);

    @Mapping(source = "containerType", target = "containerType")
    ContainerSizeDTO toContainerSizeDTO(ContainerSize containerSize);

    @Mapping(target = "containerType", source = "containerType")
    ContainerSize toContainerSize(ContainerSizeDTO containerSizeDTO);



    ContainerHistoryDTO toContainerHistoryDTO(ContainerHistory containerHistory);

    ContainerHistory toContainerHistory(ContainerHistoryDTO containerHistoryDTO);


    ShipScheduleDTO toShipScheduleDTO(ShipSchedule shipSchedule);


    ShipSchedule toShipSchedule(ShipScheduleDTO shipScheduleDTO);

    ShipDTO toShipDTO(Ship ship);

    Ship toShip(ShipDTO shipDTO);

    ScheduleDTO toScheduleDTO(Schedule schedule);

    Schedule toSchedule(ScheduleDTO scheduleDTO);

    WaypointDTO toWaypointDTO(Waypoint waypoint);

    Waypoint toWaypoint(WaypointDTO waypointDTO);


}
