package com.ctms.test;


import com.ctms.dto.RouteSegmentDTO;
import com.ctms.entity.RouteSegment;
import com.ctms.mapper.RouteSegmentDTOMapper;
import com.ctms.repository.RouteSegmentRepository;
import com.ctms.service.RouteSegmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RouteSegmentServiceTest {

    @Mock
    private RouteSegmentRepository routeSegmentRepository;

    @Mock
    private RouteSegmentDTOMapper routeSegmentDTOMapper;

    @InjectMocks
    private RouteSegmentService routeSegmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRouteSegmentsByRouteId_Success() {
        // Given
        Integer routeId = 1;
        RouteSegment routeSegment = new RouteSegment();
        RouteSegmentDTO routeSegmentDTO = new RouteSegmentDTO(2, 44, 67, 22);

        when(routeSegmentRepository.findByRouteId(routeId))
                .thenReturn(Stream.of(routeSegment).collect(Collectors.toList()));
        when(routeSegmentDTOMapper.apply(routeSegment)).thenReturn(routeSegmentDTO);

        // When
        List<RouteSegmentDTO> result = routeSegmentService.getRouteSegmentsByRouteId(routeId);

        // Then
        assertEquals(1, result.size());
        assertEquals(routeSegmentDTO, result.get(0));
        verify(routeSegmentRepository, times(1)).findByRouteId(routeId);
        verify(routeSegmentDTOMapper, times(1)).apply(routeSegment);
    }

    @Test
    void testGetRouteSegmentById_Success() {
        // Given
        Integer id = 1;
        RouteSegment routeSegment = new RouteSegment();

        when(routeSegmentRepository.findById(id)).thenReturn(Optional.of(routeSegment));

        // When
        RouteSegment result = routeSegmentService.getRouteSegmentById(id);

        // Then
        assertEquals(routeSegment, result);
        verify(routeSegmentRepository, times(1)).findById(id);
    }

    @Test
    void testGetRouteSegmentById_NotFound() {
        // Given
        Integer id = 1;

        when(routeSegmentRepository.findById(id)).thenReturn(Optional.empty());

        // When/Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            routeSegmentService.getRouteSegmentById(id);
        });

        assertEquals("RouteSegment not found", exception.getMessage());
        verify(routeSegmentRepository, times(1)).findById(id);
    }

    @Test
    void testGetRouteSegments_Success() {
        // Given
        RouteSegment routeSegment = new RouteSegment();
        RouteSegmentDTO routeSegmentDTO = new RouteSegmentDTO(2, 44, 67, 22);

        when(routeSegmentRepository.findAll())
                .thenReturn(Stream.of(routeSegment).collect(Collectors.toList()));
        when(routeSegmentDTOMapper.apply(routeSegment)).thenReturn(routeSegmentDTO);

        // When
        List<RouteSegmentDTO> result = routeSegmentService.getRouteSegments();

        // Then
        assertEquals(1, result.size());
        assertEquals(routeSegmentDTO, result.get(0));
        verify(routeSegmentRepository, times(1)).findAll();
        verify(routeSegmentDTOMapper, times(1)).apply(routeSegment);
    }
}
