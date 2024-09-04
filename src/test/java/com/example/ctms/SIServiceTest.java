package com.example.ctms;

import com.example.ctms.dto.SIDTO;
import com.example.ctms.entity.CargoType;
import com.example.ctms.entity.EmptyContainer;
import com.example.ctms.entity.SI;
import com.example.ctms.mapper.SIDTOMapper;
import com.example.ctms.repository.CargoTypeRepository;
import com.example.ctms.repository.EmptyContainerRepository;
import com.example.ctms.repository.SIRepository;
import com.example.ctms.service.SIService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SIServiceTest {

    @Mock
    private SIRepository siRepository;

    @Mock
    private EmptyContainerRepository emptyContainerRepository;

    @Mock
    private CargoTypeRepository cargoTypeRepository;

    @Mock
    private SIDTOMapper sidtoMapper;

    @InjectMocks
    private SIService siService;

    private EmptyContainer emptyContainer;
    private CargoType cargoType;
    private SI si;
    private SIDTO siDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        emptyContainer = new EmptyContainer();
        emptyContainer.setId(1);

        cargoType = new CargoType();
        cargoType.setId(1);

        si = new SI();
        si.setId(1);
        si.setEmptyContainer(emptyContainer);
        si.setCargoType(cargoType);
        si.setCargoWeight(100.0);
        si.setCargoVolume(50.0);

        siDTO = new SIDTO(1, 1, 10, 100.0, 50.0);

        when(sidtoMapper.apply(si)).thenReturn(siDTO);
        when(sidtoMapper.apply(any(SI.class))).thenReturn(siDTO);
    }

    @Test
    void testGetAllSIs() {
        when(siRepository.findAll()).thenReturn(List.of(si));

        List<SIDTO> result = siService.getAllSIs();

        assertEquals(1, result.size());
        verify(siRepository, times(1)).findAll();
    }

    @Test
    void testGetSIById() {
        when(siRepository.findById(1)).thenReturn(Optional.of(si));

        Optional<SIDTO> result = siService.getSIById(1);

        assertTrue(result.isPresent());
        assertEquals(si.getId(), result.get().getId());
        verify(siRepository, times(1)).findById(1);
    }

    @Test
    void testCreateSI() {
        when(emptyContainerRepository.findById(1)).thenReturn(Optional.of(emptyContainer));
        when(cargoTypeRepository.findById(1)).thenReturn(Optional.of(cargoType));
        when(siRepository.save(any(SI.class))).thenReturn(si);

        SI result = siService.createSI(siDTO);

        assertNotNull(result);
        assertEquals(siDTO.getCargoWeight(), result.getCargoWeight());
        verify(emptyContainerRepository, times(1)).findById(1);
        verify(cargoTypeRepository, times(1)).findById(1);
        verify(siRepository, times(1)).save(any(SI.class));
    }

    @Test
    void testUpdateSI() {
        when(siRepository.findById(1)).thenReturn(Optional.of(si));
        when(emptyContainerRepository.findById(1)).thenReturn(Optional.of(emptyContainer));
        when(cargoTypeRepository.findById(1)).thenReturn(Optional.of(cargoType));
        when(siRepository.save(any(SI.class))).thenReturn(si);

        SI result = siService.updateSI(1, siDTO);

        assertNotNull(result);
        assertEquals(siDTO.getCargoWeight(), result.getCargoWeight());
        verify(siRepository, times(1)).findById(1);
        verify(emptyContainerRepository, times(1)).findById(1);
        verify(cargoTypeRepository, times(1)).findById(1);
        verify(siRepository, times(1)).save(any(SI.class));
    }

    @Test
    void testDeleteSI() {
        doNothing().when(siRepository).deleteById(1);

        siService.deleteSI(1);

        verify(siRepository, times(1)).deleteById(1);
    }

    @Test
    void testGetAllCargo() {
        when(cargoTypeRepository.findAll()).thenReturn(List.of(cargoType));

        List<CargoType> result = siService.getAllCargo();

        assertEquals(1, result.size());
        verify(cargoTypeRepository, times(1)).findAll();
    }
}
