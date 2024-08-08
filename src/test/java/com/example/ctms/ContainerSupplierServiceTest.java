package com.example.ctms;

import com.example.ctms.dto.ContainerUpdateRequest;
import com.example.ctms.entity.ContainerSupplier;
import com.example.ctms.repository.ContainerSupplierRepository;
import com.example.ctms.service.ContainerSupplierService;
import com.example.ctms.test.ImageUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class ContainerSupplierServiceTest {

    @Mock
    private ContainerSupplierRepository containerSupplierRepository;

    @InjectMocks
    private ContainerSupplierService containerSupplierService;

    private ContainerSupplier containerSupplier1;
    private ContainerSupplier containerSupplier2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample data
        containerSupplier1 = new ContainerSupplier("Supplier A", "Address A", "123456789", "emailA@example.com", "www.supplierA.com", "Repair", null);
        containerSupplier2 = new ContainerSupplier("Supplier B", "Address B", "987654321", "emailB@example.com", "www.supplierB.com", "Repair", null);
    }

    @Test
    public void testInsertSupplier() throws IOException {
        // Arrange
        doNothing().when(containerSupplierRepository).save(any(ContainerSupplier.class));

        // Act
        containerSupplierService.insert_supplier(containerSupplier1);

        // Assert
        verify(containerSupplierRepository, times(1)).save(containerSupplier1);
    }

    @Test
    public void testGetCustomerById() {
        // Arrange
        byte[] imageData = new byte[]{1, 2, 3}; // Sample image data
        containerSupplier1.setImageData(imageData);
        when(containerSupplierRepository.findById(anyInt())).thenReturn(Optional.of(containerSupplier1));
        when(ImageUtils.decompressImage(imageData)).thenReturn(imageData);

        // Act
        byte[] result = containerSupplierService.getCustomerById(1);

        // Assert
        assertArrayEquals(imageData, result);
    }

    @Test
    public void testGetAllSuppliers() {
        // Arrange
        when(containerSupplierRepository.findAll()).thenReturn(Arrays.asList(containerSupplier1, containerSupplier2));

        // Act
        List<ContainerSupplier> result = containerSupplierService.getAllSupplers();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(containerSupplier1));
        assertTrue(result.contains(containerSupplier2));
    }

    @Test
    public void testUpdateSupplier() throws IOException {
        byte[] compressedImage = new byte[]{4, 5, 6}; // Sample compressed image data

        // Arrange
        containerSupplier1.setName("Old Name");
        containerSupplier1.setAddress("Old Address");
        containerSupplier1.setEmail("old@example.com");
        containerSupplier1.setPhoneNumber("0987654321");
        containerSupplier1.setDetailService("Old Service");
        containerSupplier1.setWebsite("oldwebsite.com");
        containerSupplier1.setImageData(compressedImage) ;
        when(containerSupplierRepository.getReferenceById(anyInt())).thenReturn(containerSupplier1);

        ContainerUpdateRequest updateRequest = new ContainerUpdateRequest(
                "New Name", "New Address", "0987654321", "old@example.com", "oldwebsite.com", "Old Service",compressedImage
        );

        MultipartFile image = mock(MultipartFile.class);
        when(image.getBytes()).thenReturn(compressedImage);
        when(image.isEmpty()).thenReturn(false);
        when(ImageUtils.compressImage(image.getBytes())).thenReturn(compressedImage);

        // Act
        containerSupplierService.updateSupplier(1, updateRequest, image);

        // Assert
        assertEquals("New Name", containerSupplier1.getName());
        assertEquals("New Address", containerSupplier1.getAddress());
        assertEquals("old@example.com", containerSupplier1.getEmail());
        assertEquals("0987654321", containerSupplier1.getPhoneNumber());
        assertEquals("Old Service", containerSupplier1.getDetailService());
        assertEquals("oldwebsite.com", containerSupplier1.getWebsite());
        assertEquals(compressedImage, compressedImage);

    }

    @Test
    public void testDeleteSupplierById() {
        // Arrange
        doNothing().when(containerSupplierRepository).deleteById(anyInt());

        // Act
        containerSupplierService.deleteSupplierById(1);

        // Assert
        verify(containerSupplierRepository, times(1)).deleteById(anyInt());
    }
}
