package com.ctms.controller;

import com.ctms.dto.ShipScheduleDTO;
import com.ctms.service.ShipScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shipSchedules")
public class ShipScheduleController {
    @Autowired
    private ShipScheduleService shipScheduleService;

    @GetMapping
    public List<ShipScheduleDTO> getAllShipSchedules() {
        return shipScheduleService.getAllShipSchedules();
    }

    @GetMapping("/delivery")
    public List<ShipScheduleDTO> getShipSchedulesByScheduleId(@RequestParam int scheduleId) {
        return shipScheduleService.getShipSchedulesByScheduleId(scheduleId);
    }
}
