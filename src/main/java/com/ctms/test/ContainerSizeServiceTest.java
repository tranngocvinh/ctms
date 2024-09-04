package com.ctms.test;

import com.ctms.dto.ContainerSizeDTO;
import com.ctms.dto.ContainerTypeDTO;
import com.ctms.entity.ContainerSize;
import com.ctms.entity.ContainerType;
import com.ctms.mapper.ContainerMapper;
import com.ctms.repository.ContainerSizeRepository;
import com.ctms.repository.ContainerTypeRepository;
import com.ctms.repository.ScheduleRepository;
import com.ctms.repository.ShipRepository;
import com.ctms.service.ContainerSizeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class ContainerSizeServiceTest {

    @Mock
    private ContainerSizeRepository containerSizeRepository;

    @Mock
    private ContainerTypeRepository containerTypeRepository;

    @Mock
    private ShipRepository shipRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ContainerSizeService containerSizeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllSizes() {
        // Arrange
        ContainerType type20FeetNormal = new ContainerType("20 feet", "Normal");
        ContainerSize size = new ContainerSize(6.0, 2.4, 2.6, 37.44, 2000.1, 28000.0, 30000.0, type20FeetNormal);
        when(containerSizeRepository.findAll()).thenReturn(List.of(size));

        ContainerSizeDTO sizeDTO = ContainerMapper.INSTANCE.toContainerSizeDTO(size);

        // Act
        List<ContainerSizeDTO> result = containerSizeService.getAllSizes();

        // Assert
        assertEquals(1, result.size());
        assertEquals(sizeDTO, result.get(0));
    }

    @Test
    public void testGetSizesByType() {
        // Arrange
        ContainerType type20FeetNormal = new ContainerType("20 feet", "Normal");
        ContainerSize size = new ContainerSize(6.0, 2.4, 2.6, 37.44, 2000.1, 28000.0, 30000.0, type20FeetNormal);
        when(containerSizeRepository.findByContainerTypeId(anyInt())).thenReturn(List.of(size));

        ContainerSizeDTO sizeDTO = ContainerMapper.INSTANCE.toContainerSizeDTO(size);

        // Act
        List<ContainerSizeDTO> result = containerSizeService.getSizesByType(1);

        // Assert
        assertEquals(1, result.size());
        assertEquals(sizeDTO, result.get(0));
    }

    @Test
    public void testAddSize() {
        // Arrange
        ContainerType containerType = new ContainerType();
        containerType.setId(1);
        when(containerTypeRepository.findById(anyInt())).thenReturn(Optional.of(containerType));

        ContainerSize size = new ContainerSize(6.0, 2.4, 2.6, 37.44, 2000.1, 28000.0, 30000.0, containerType);
        when(containerSizeRepository.save(any(ContainerSize.class))).thenReturn(size);

        ContainerTypeDTO containerTypeDTO = new ContainerTypeDTO(1, "20 feet", "Normal");
        ContainerSizeDTO sizeDTO = new ContainerSizeDTO(
                null, 6.0, 2.4, 2.6, 37.44, 200.12, 28000.0, 30000.2, containerTypeDTO
        );

        // Act
        ContainerSizeDTO result = containerSizeService.addSize(sizeDTO);

        // Assert
        assertEquals(sizeDTO.length(), result.length());
        assertEquals(sizeDTO.width(), result.width());
        assertEquals(sizeDTO.height(), result.height());
        assertEquals(sizeDTO.volume(), result.volume());
        assertEquals(sizeDTO.weight(), result.weight());
        assertEquals(sizeDTO.loadCapacity(), result.loadCapacity());
        assertEquals(sizeDTO.maxLoad(), result.maxLoad());
        assertEquals(sizeDTO.containerType().id(), result.containerType().id());

        verify(containerTypeRepository, times(1)).findById(anyInt());
        verify(containerSizeRepository, times(1)).save(any(ContainerSize.class));
    }

    @Test
    public void testAddSize_ContainerTypeNotFound() {
        // Arrange
        when(containerTypeRepository.findById(anyInt())).thenReturn(Optional.empty());

        ContainerTypeDTO containerTypeDTO = new ContainerTypeDTO(1, "20 feet", "Normal");
        ContainerSizeDTO sizeDTO = new ContainerSizeDTO(
                null, 6.0, 2.4, 2.6, 37.44, 2000.1, 28000.0, 30000.0, containerTypeDTO
        );

        // Act & Assert
        assertThrows(RuntimeException.class, () -> containerSizeService.addSize(sizeDTO));
        verify(containerTypeRepository, times(1)).findById(anyInt());
        verify(containerSizeRepository, never()).save(any(ContainerSize.class));
    }

    @Test
    public void testUpdateSize() {
        // Arrange
        ContainerType containerType = new ContainerType();
        containerType.setId(1);
        when(containerTypeRepository.findById(anyInt())).thenReturn(Optional.of(containerType));

        ContainerSize size = new ContainerSize(6.0, 2.4, 2.6, 37.44, 2000.1, 28000.0, 30000.0, containerType);
        when(containerSizeRepository.findById(anyInt())).thenReturn(Optional.of(size));
        when(containerSizeRepository.save(any(ContainerSize.class))).thenReturn(size);

        ContainerTypeDTO containerTypeDTO = new ContainerTypeDTO(1, "20 feet", "Normal");
        ContainerSizeDTO sizeDTO = new ContainerSizeDTO(
                1, 6.0, 2.4, 2.6, 37.44, 2000.1, 28000.0, 30000.0, containerTypeDTO
        );

        // Act
        ContainerSizeDTO result = containerSizeService.updateSize(1, sizeDTO);

        // Assert
        assertEquals(sizeDTO.length(), result.length());
        assertEquals(sizeDTO.width(), result.width());
        assertEquals(sizeDTO.height(), result.height());
        assertEquals(sizeDTO.volume(), result.volume());
        assertEquals(sizeDTO.weight(), result.weight());
        assertEquals(sizeDTO.loadCapacity(), result.loadCapacity());
        assertEquals(sizeDTO.maxLoad(), result.maxLoad());
        assertEquals(sizeDTO.containerType().id(), result.containerType().id());

        verify(containerTypeRepository, times(1)).findById(anyInt());
        verify(containerSizeRepository, times(1)).findById(anyInt());
        verify(containerSizeRepository, times(1)).save(any(ContainerSize.class));
    }

    @Test
    public void testUpdateSize_ContainerTypeNotFound() {
        // Arrange
        when(containerTypeRepository.findById(anyInt())).thenReturn(Optional.empty());

        ContainerTypeDTO containerTypeDTO = new ContainerTypeDTO(1, "20 feet", "Normal");
        ContainerSizeDTO sizeDTO = new ContainerSizeDTO(
                1, 6.0, 2.4, 2.6, 37.44, 2000.1, 28000.0, 30000.0, containerTypeDTO
        );

        // Act & Assert
        assertThrows(RuntimeException.class, () -> containerSizeService.updateSize(1, sizeDTO));
        verify(containerTypeRepository, times(1)).findById(anyInt());
        verify(containerSizeRepository, never()).findById(anyInt());
        verify(containerSizeRepository, never()).save(any(ContainerSize.class));
    }

    @Test
    public void testUpdateSize_ContainerSizeNotFound() {
        // Arrange
        ContainerType containerType = new ContainerType();
        containerType.setId(1);
        when(containerTypeRepository.findById(anyInt())).thenReturn(Optional.of(containerType));
        when(containerSizeRepository.findById(anyInt())).thenReturn(Optional.empty());

        ContainerTypeDTO containerTypeDTO = new ContainerTypeDTO(1, "20 feet", "Normal");
        ContainerSizeDTO sizeDTO = new ContainerSizeDTO(
                1, 6.0, 2.4, 2.6, 37.44, 2000.1, 28000.0, 30000.0, containerTypeDTO
        );

        // Act & Assert
        assertThrows(RuntimeException.class, () -> containerSizeService.updateSize(1, sizeDTO));
        verify(containerTypeRepository, times(1)).findById(anyInt());
        verify(containerSizeRepository, never()).findById(anyInt());
        verify(containerSizeRepository, never()).save(any(ContainerSize.class));
    }

    @Test
    public void testDeleteSize() {
        // Arrange
        doNothing().when(containerSizeRepository).deleteById(anyInt());

        // Act
        containerSizeService.deleteSize(1);

        // Assert
        verify(containerSizeRepository, times(1)).deleteById(anyInt());
    }

    @Test
    public void testInitializeContainerTypes() {
        // Arrange
        when(containerTypeRepository.count()).thenReturn(0L);

        // Act
        containerSizeService.initializeContainerTypes();

        // Assert
        verify(containerTypeRepository, times(4)).save(any(ContainerType.class));
    }

    @Test
    public void testInitializeContainerTypes_AlreadyInitialized() {
        // Arrange
        when(containerTypeRepository.count()).thenReturn(4L);

        // Act
        containerSizeService.initializeContainerTypes();

        // Assert
        verify(containerTypeRepository, never()).save(any(ContainerType.class));
    }
}