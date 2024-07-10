package com.example.ctms.service;

import com.example.ctms.dto.ShipScheduleDTO;
import com.example.ctms.entity.ShipSchedule;
import com.example.ctms.mapper.ShipScheduleMapper;
import com.example.ctms.repository.ShipScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShipScheduleService  {
    private final ShipScheduleRepository shipScheduleRepository;

    public ShipScheduleService(ShipScheduleRepository shipScheduleRepository) {
        this.shipScheduleRepository = shipScheduleRepository;
    }

    public List<ShipScheduleDTO> getAllShipSchedules() {
        List<ShipSchedule> shipSchedules = shipScheduleRepository.findAll();
        Set<ShipScheduleKey> uniqueKeys = new HashSet<>();

        return shipSchedules.stream()
                .filter(schedule -> uniqueKeys.add(new ShipScheduleKey(schedule.getShip().getId(), schedule.getSchedule().getId())))
                .map(ShipScheduleMapper::toDTO)
                .collect(Collectors.toList());
    }

    private static class ShipScheduleKey {
        private final Integer shipId;
        private final Integer scheduleId;

        public ShipScheduleKey(Integer shipId, Integer scheduleId) {
            this.shipId = shipId;
            this.scheduleId = scheduleId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ShipScheduleKey that = (ShipScheduleKey) o;

            if (!shipId.equals(that.shipId)) return false;
            return scheduleId.equals(that.scheduleId);
        }

        @Override
        public int hashCode() {
            int result = shipId.hashCode();
            result = 31 * result + scheduleId.hashCode();
            return result;
        }
    }
}
