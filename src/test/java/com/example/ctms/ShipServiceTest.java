package com.example.ctms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.ctms.dto.ShipDTO;
import com.example.ctms.entity.Ship;
import com.example.ctms.repository.ShipRepository;
import com.example.ctms.service.ShipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ShipServiceTest {

    @Mock
    private ShipRepository shipRepository;

    @InjectMocks
    private ShipService shipService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllShips() {
        // Arrange
        Ship ship1 = new Ship("Ship1", "Company1", 1000.0, "Reg123", 2000, "Active");
        Ship ship2 = new Ship("Ship2", "Company2", 2000.0, "Reg456", 2010, "Inactive");

        List<Ship> ships = Arrays.asList(ship1, ship2);
        when(shipRepository.findAll()).thenReturn(ships);

        // Act
        List<ShipDTO> result = shipService.getAllShips();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Ship1", result.get(0).name());
        assertEquals("Company1", result.get(0).company());
        assertEquals(1000.0, result.get(0).capacity());
        assertEquals("Reg123", result.get(0).registrationNumber());
        assertEquals(2000, result.get(0).yearBuilt());
        assertEquals("Active", result.get(0).status());

        assertEquals("Ship2", result.get(1).name());
        assertEquals("Company2", result.get(1).company());
        assertEquals(2000.0, result.get(1).capacity());
        assertEquals("Reg456", result.get(1).registrationNumber());
        assertEquals(2010, result.get(1).yearBuilt());
        assertEquals("Inactive", result.get(1).status());
    }

    @Test
    public void testAddShip() {
        // Arrange
        Ship ship = new Ship("Ship1", "Company1", 1000.0, "Reg123", 2000, "Active");
        ship.setId(1);
        when(shipRepository.save(any(Ship.class))).thenReturn(ship);

        ShipDTO shipDTO = new ShipDTO(1, "Ship1", "Company1", 1000.0, "Reg123", 2000, "Active");

        // Act
        ShipDTO result = shipService.addShip(shipDTO);

        // Assert
        assertEquals(shipDTO, result);
        verify(shipRepository, times(1)).save(any(Ship.class));
    }

    @Test
    public void testUpdateShip() {
        // Arrange
        Ship ship = new Ship("Ship1", "Company1", 1000.0, "Reg123", 2000, "Active");
        when(shipRepository.findById(anyInt())).thenReturn(Optional.of(ship));
        when(shipRepository.save(any(Ship.class))).thenReturn(ship);

        ShipDTO shipDTO = new ShipDTO(1, "ShipUpdated", "CompanyUpdated", 2000.0, "Reg1234", 2010, "Inactive");

        // Act
        ShipDTO result = shipService.updateShip(1, shipDTO);

        // Assert
        assertEquals(shipDTO.name(), result.name());
        assertEquals(shipDTO.company(), result.company());
        assertEquals(shipDTO.capacity(), result.capacity());
        assertEquals(shipDTO.registrationNumber(), result.registrationNumber());
        assertEquals(shipDTO.yearBuilt(), result.yearBuilt());
        assertEquals(shipDTO.status(), result.status());

        verify(shipRepository, times(1)).findById(anyInt());
        verify(shipRepository, times(1)).save(any(Ship.class));
    }

    @Test
    public void testUpdateShip_NotFound() {
        // Arrange
        when(shipRepository.findById(anyInt())).thenReturn(Optional.empty());

        ShipDTO shipDTO = new ShipDTO(1, "ShipUpdated", "CompanyUpdated", 2000.0, "Reg1234", 2010, "Inactive");

        // Act & Assert
        assertThrows(RuntimeException.class, () -> shipService.updateShip(1, shipDTO));
        verify(shipRepository, times(1)).findById(anyInt());
        verify(shipRepository, never()).save(any(Ship.class));
    }

    @Test
    public void testDeleteShip() {
        // Arrange
        doNothing().when(shipRepository).deleteById(anyInt());

        // Act
        shipService.deleteShip(1);

        // Assert
        verify(shipRepository, times(1)).deleteById(anyInt());
    }

}
