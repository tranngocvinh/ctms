package com.ctms.test;


import com.ctms.dto.RepairDTO;
import com.ctms.entity.Container;
import com.ctms.entity.ContainerSupplier;
import com.ctms.entity.Customer;
import com.ctms.entity.Repair;
import com.ctms.mapper.RepairDTOMapper;
import com.ctms.repository.ContainerRepository;
import com.ctms.repository.ContainerSupplierRepository;
import com.ctms.repository.RepairRepository;
import com.ctms.service.CustomerService;
import com.ctms.service.RepairService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RepairServiceTest {

    @Mock
    private RepairRepository repairRepository;

    @Mock
    private ContainerRepository containerRepository;

    @Mock
    private ContainerSupplierRepository containerSupplierRepository;

    @Mock
    private RepairDTOMapper repairDTOMapper;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private RepairService repairService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRepair_Success() {
        // Given
        Repair repair = new Repair();
        Container container = new Container();
        container.setContainerCode("C123");
        repair.setContainer(container);

        ContainerSupplier supplier = new ContainerSupplier();
        supplier.setSupplierId(1);
        repair.setSupplier(supplier);

        when(containerRepository.findById("C123")).thenReturn(Optional.of(container));
        when(containerSupplierRepository.findById(1)).thenReturn(Optional.of(supplier));
        when(repairRepository.save(repair)).thenReturn(repair);

        // When
        Repair result = repairService.createRepair(repair);

        // Then
        assertEquals(container, result.getContainer());
        assertEquals(supplier, result.getSupplier());
        assertEquals("Under Maintenance", container.getStatus());
        verify(containerRepository, times(1)).findById("C123");
        verify(containerSupplierRepository, times(1)).findById(1);
        verify(repairRepository, times(1)).save(repair);
    }

    @Test
    void testCreateRepair_ContainerNotFound() {
        // Given
        Repair repair = new Repair();
        Container container = new Container();
        container.setContainerCode("C123");
        repair.setContainer(container);

        when(containerRepository.findById("C123")).thenReturn(Optional.empty());

        // When/Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            repairService.createRepair(repair);
        });

        assertEquals("Container not found with id C123", exception.getMessage());
        verify(containerRepository, times(1)).findById("C123");
        verify(containerSupplierRepository, never()).findById(anyInt());
        verify(repairRepository, never()).save(any(Repair.class));
    }

    @Test
    void testCreateRepair_SupplierNotFound() {
        // Given
        Repair repair = new Repair();
        Container container = new Container();
        container.setContainerCode("C123");
        repair.setContainer(container);

        ContainerSupplier supplier = new ContainerSupplier();
        supplier.setSupplierId(1);
        repair.setSupplier(supplier);

        when(containerRepository.findById("C123")).thenReturn(Optional.of(container));
        when(containerSupplierRepository.findById(1)).thenReturn(Optional.empty());

        // When/Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            repairService.createRepair(repair);
        });

        assertEquals("Supplier not found with id 1", exception.getMessage());
        verify(containerRepository, times(1)).findById("C123");
        verify(containerSupplierRepository, times(1)).findById(1);
        verify(repairRepository, never()).save(any(Repair.class));
    }

    @Test
    void testGetAllRepair_Success() {
        // Given
        Customer customer = new Customer();
        customer.setId(1);
        customer.setRoles(List.of("STAFF"));

        Repair repair = new Repair();
        RepairDTO repairDTO = new RepairDTO();

        when(customerService.getCurrentCustomer()).thenReturn(customer);
        when(repairRepository.findAll()).thenReturn(List.of(repair));
        when(repairDTOMapper.apply(repair)).thenReturn(repairDTO);

        // When
        List<RepairDTO> result = repairService.getAllRepair();

        // Then
        assertEquals(1, result.size());
        verify(customerService, times(1)).getCurrentCustomer();
        verify(repairRepository, times(1)).findAll();
        verify(repairDTOMapper, times(1)).apply(repair);
    }

    @Test
    void testGetRepairById_Success() {
        // Given
        Repair repair = new Repair();
        RepairDTO repairDTO = new RepairDTO();

        when(repairRepository.findById(1L)).thenReturn(Optional.of(repair));
        when(repairDTOMapper.apply(repair)).thenReturn(repairDTO);

        // When
        Optional<RepairDTO> result = repairService.getRepairById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(repairDTO, result.get());
        verify(repairRepository, times(1)).findById(1L);
        verify(repairDTOMapper, times(1)).apply(repair);
    }

    @Test
    void testUpdateRepair_Success() {
        // Given
        Repair repair = new Repair();
        RepairDTO repairDTO = new RepairDTO(2, 1, 100.0,"abc", new Date(), "Repair description");

        Container container = new Container();
        container.setContainerCode("C123");

        ContainerSupplier supplier = new ContainerSupplier();
        supplier.setSupplierId(1);

        when(repairRepository.findById(1L)).thenReturn(Optional.of(repair));
        when(containerRepository.findById("C123")).thenReturn(Optional.of(container));
        when(containerSupplierRepository.findById(1)).thenReturn(Optional.of(supplier));

        // When
        repairService.updateRepair(1L, repairDTO);

        // Then
        verify(repairRepository, times(1)).save(repair);
        assertEquals(container, repair.getContainer());
        assertEquals(supplier, repair.getSupplier());
        assertEquals(100.0, repair.getRepairCost());
    }

    @Test
    void testHandlePayment_Success() {
        // Given
        Repair repair = new Repair();
        repair.setIsPayment(0);

        when(repairRepository.findById(1L)).thenReturn(Optional.of(repair));

        // When
        repairService.handlePayment(1L);

        // Then
        assertEquals(1, repair.getIsPayment());
        verify(repairRepository, times(1)).save(repair);
    }

    @Test
    void testRepairFinish_Success() {
        // Given
        Repair repair = new Repair();
        Container container = new Container();

        repair.setContainer(container);
        repair.setIsRepair(1);

        when(repairRepository.findById(1L)).thenReturn(Optional.of(repair));

        // When
        repairService.repairFinish(1L);

        // Then
        assertEquals("In Port", container.getStatus());
        assertEquals(0, container.getIsRepair());
        verify(containerRepository, times(1)).save(container);
    }
}
