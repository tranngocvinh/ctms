package com.example.ctms;

import com.example.ctms.dto.DropOrderDTO;
import com.example.ctms.entity.*;
import com.example.ctms.mapper.DropOrderDTOMapper;
import com.example.ctms.repository.DropOrderRepository;
import com.example.ctms.repository.SIRepository;
import com.example.ctms.service.DropOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DropOrderServiceTest {

    @Mock
    private DropOrderRepository dropOrderRepository;

    @Mock
    private SIRepository siRepository;

    @Mock
    private DropOrderDTOMapper dropOrderDTOMapper;

    @InjectMocks
    private DropOrderService dropOrderService;

    private DropOrder dropOrder;
    private DropOrderDTO dropOrderDTO;
    private SI si;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        PortLocation portLocation = new PortLocation("haiphong",12.6,24.9);
        Ship ship = new Ship("nanhngu","nanhsieungu",12.9,"13232",2024,"good");
        EmptyContainer emptyContainer = new EmptyContainer(12,LocalDateTime.now(),LocalDateTime.now(),portLocation,ship,true,1);
        si = new SI();
        si.setId(1);
        si.setEmptyContainer(emptyContainer); // Assuming EmptyContainer has approvalDate field

        dropOrder = new DropOrder(si, LocalDateTime.now(), "Location A", "Pending", 0.0);
        dropOrderDTO = new DropOrderDTO(1, LocalDateTime.now(), "Location A", "Pending");
    }

    @Test
    public void testGetAllDropOrders() {
        // Arrange
        when(dropOrderRepository.findAll()).thenReturn(List.of(dropOrder));
        when(dropOrderDTOMapper.apply(dropOrder)).thenReturn(dropOrderDTO);

        // Act
        List<DropOrderDTO> dropOrders = dropOrderService.getAllDropOrders();

        // Assert
        assertEquals(1, dropOrders.size());
        verify(dropOrderRepository, times(1)).findAll();
    }

    @Test
    public void testGetDropOrderById() {
        // Arrange
        when(dropOrderRepository.findById(1)).thenReturn(Optional.of(dropOrder));
        when(dropOrderDTOMapper.apply(dropOrder)).thenReturn(dropOrderDTO);

        // Act
        Optional<DropOrderDTO> result = dropOrderService.getDropOrderById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(dropOrderDTO, result.get());
        verify(dropOrderRepository, times(1)).findById(1);
    }

    @Test
    public void testCreateDropOrder() {
        // Arrange
        when(siRepository.findById(1)).thenReturn(Optional.of(si));
        when(dropOrderRepository.save(any(DropOrder.class))).thenReturn(dropOrder);
        when(dropOrderDTOMapper.apply(dropOrder)).thenReturn(dropOrderDTO);

        // Act
        DropOrder result = dropOrderService.createDropOrder(dropOrderDTO);

        // Assert
        assertNotNull(result);
        assertEquals(dropOrder.getSi(), result.getSi());
        verify(siRepository, times(1)).findById(1);
        verify(dropOrderRepository, times(1)).save(any(DropOrder.class));
    }

    @Test
    public void testUpdateDropOrder() {
        // Arrange
        when(dropOrderRepository.findById(1)).thenReturn(Optional.of(dropOrder));
        when(dropOrderRepository.save(any(DropOrder.class))).thenReturn(dropOrder);

        DropOrder updatedDropOrder = new DropOrder(si, LocalDateTime.now(), "Updated Location", "Completed", 0.0);

        // Act
        DropOrder result = dropOrderService.updateDropOrder(1, updatedDropOrder);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Location", result.getDropLocation());
        assertEquals("Completed", result.getStatus());
        verify(dropOrderRepository, times(1)).findById(1);
        verify(dropOrderRepository, times(1)).save(any(DropOrder.class));
    }

    @Test
    public void testDeleteDropOrder() {
        // Act
        dropOrderService.deleteDropOrder(1);

        // Assert
        verify(dropOrderRepository, times(1)).deleteById(1);
    }
}
