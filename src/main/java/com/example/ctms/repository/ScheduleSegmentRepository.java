package com.example.ctms.repository;

import com.example.ctms.entity.Schedule;
import com.example.ctms.entity.ScheduleSegment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleSegmentRepository extends JpaRepository<ScheduleSegment, Integer> {
    List<ScheduleSegment> findBySchedule(Schedule schedule);
    void deleteByScheduleId(Integer scheduleId);
}
