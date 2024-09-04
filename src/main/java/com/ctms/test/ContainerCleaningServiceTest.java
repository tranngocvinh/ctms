package com.ctms.test;


import com.ctms.entity.Cleaning;
import com.ctms.entity.Container;
import com.ctms.entity.ContainerSupplier;
import com.ctms.repository.ContainerCleaningRepository;
import com.ctms.repository.ContainerRepository;
import com.ctms.repository.ContainerSupplierRepository;
import com.ctms.service.ContainerCleaningService;
import com.ctms.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ContainerCleaningServiceTest {

    @Mock
    private ContainerCleaningRepository containerCleaningRepository;

    @Mock
    private ContainerRepository containerRepository;

    @Mock
    private ContainerSupplierRepository containerSupplierRepository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private ContainerCleaningService containerCleaningService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCleaning_Success() {
        // Given
        Cleaning cleaning = new Cleaning();
        Container container = new Container();
        container.setContainerCode("C123");
        cleaning.setContainer(container);

        ContainerSupplier supplier = new ContainerSupplier();
        supplier.setSupplierId(1);
        cleaning.setSupplier(supplier);

        when(containerRepository.findById("C123")).thenReturn(Optional.of(container));
        when(containerSupplierRepository.findById(1)).thenReturn(Optional.of(supplier));
        when(containerCleaningRepository.save(cleaning)).thenReturn(cleaning);

        // When
        Cleaning result = containerCleaningService.createcLeaning(cleaning);

        // Then
        assertEquals(container, result.getContainer());
        assertEquals(supplier, result.getSupplier());
        assertEquals("Under Maintenance", container.getStatus());
        verify(containerRepository, times(1)).findById("C123");
        verify(containerSupplierRepository, times(1)).findById(1);
        verify(containerCleaningRepository, times(1)).save(cleaning);
    }

    @Test
    void testCreateCleaning_ContainerNotFound() {
        // Given
        Cleaning cleaning = new Cleaning();
        Container container = new Container();
        container.setContainerCode("C123");
        cleaning.setContainer(container);

        when(containerRepository.findById("C123")).thenReturn(Optional.empty());

        // When/Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            containerCleaningService.createcLeaning(cleaning);
        });

        assertEquals("Container not found with id C123", exception.getMessage());
        verify(containerRepository, times(1)).findById("C123");
        verify(containerSupplierRepository, never()).findById(anyInt());
        verify(containerCleaningRepository, never()).save(any(Cleaning.class));
    }

    @Test
    void testCreateCleaning_SupplierNotFound() {
        // Given
        Cleaning cleaning = new Cleaning();
        Container container = new Container();
        container.setContainerCode("C123");
        cleaning.setContainer(container);

        ContainerSupplier supplier = new ContainerSupplier();
        supplier.setSupplierId(1);
        cleaning.setSupplier(supplier);

        when(containerRepository.findById("C123")).thenReturn(Optional.of(container));
        when(containerSupplierRepository.findById(1)).thenReturn(Optional.empty());

        // When/Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            containerCleaningService.createcLeaning(cleaning);
        });

        assertEquals("Supplier not found with id 1", exception.getMessage());
        verify(containerRepository, times(1)).findById("C123");
        verify(containerSupplierRepository, times(1)).findById(1);
        verify(containerCleaningRepository, never()).save(any(Cleaning.class));
    }
}
