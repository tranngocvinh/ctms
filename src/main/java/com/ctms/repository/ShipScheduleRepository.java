package com.ctms.repository;

import com.ctms.entity.ShipSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipScheduleRepository extends JpaRepository<ShipSchedule, Long> {
    List<ShipSchedule> findByScheduleId(Integer scheduleId);
    void deleteByScheduleId(Integer scheduleId);
//    List<ShipSchedule> findAllByShipId(Integer shipId);
//    List<ShipSchedule> findAllByScheduleId(Integer scheduleId);
//
//    List<ShipSchedule> findByShipAndScheduleAndContainer(Ship ship, Schedule schedule, Container container);
//
//    List<ShipSchedule> findByShipAndScheduleAndContainerIsNull(Ship ship, Schedule schedule);
}
