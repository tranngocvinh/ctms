package com.example.ctms;

import com.example.ctms.entity.PortLocation;
import com.example.ctms.repository.PortLocationRepository;
import com.example.ctms.service.PortLocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PortLocationServiceTest {

    @InjectMocks
    private PortLocationService portLocationService;

    @Mock
    private PortLocationRepository portLocationRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPorts() {
        List<PortLocation> ports = List.of(new PortLocation());
        when(portLocationRepository.findAll()).thenReturn(ports);

        List<PortLocation> result = portLocationService.getAllPorts();

        assertEquals(1, result.size());
        verify(portLocationRepository, times(1)).findAll();
    }

    @Test
    public void testSearchPortsByName() {
        List<PortLocation> ports = List.of(new PortLocation());
        when(portLocationRepository.findByPortNameContainingIgnoreCase(anyString())).thenReturn(ports);

        List<PortLocation> result = portLocationService.searchPortsByName("name");

        assertEquals(1, result.size());
        verify(portLocationRepository, times(1)).findByPortNameContainingIgnoreCase(anyString());
    }
}
