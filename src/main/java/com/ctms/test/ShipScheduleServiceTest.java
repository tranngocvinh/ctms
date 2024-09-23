package com.ctms.test;


import com.ctms.dto.ShipScheduleDTO;
import com.ctms.entity.ShipSchedule;
import com.ctms.mapper.ShipScheduleMapper;
import com.ctms.repository.ShipScheduleRepository;
import com.ctms.service.ShipScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ShipScheduleServiceTest {

    @InjectMocks
    private ShipScheduleService shipScheduleService;

    @Mock
    private ShipScheduleRepository shipScheduleRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllShipSchedules() {
        List<ShipSchedule> shipSchedules = List.of(new ShipSchedule());
        when(shipScheduleRepository.findAll()).thenReturn(shipSchedules);

        List<ShipScheduleDTO> result = shipScheduleService.getAllShipSchedules();

        assertEquals(1, result.size());
        verify(shipScheduleRepository, times(1)).findAll();
    }
}