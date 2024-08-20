package com.example.ctms.service;

import com.example.ctms.dto.ShipDTO;
import com.example.ctms.entity.Ship;
import com.example.ctms.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShipService {

    private final ShipRepository shipRepository;

    @Autowired
    public ShipService(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    public List<ShipDTO> getAllShips() {
        List<Ship> ships = shipRepository.findAll();
        return ships.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public ShipDTO addShip(ShipDTO shipDTO) {
        Ship ship = new Ship();
        ship.setName(shipDTO.name());
        ship.setCompany(shipDTO.company());
        ship.setCapacity(shipDTO.capacity());
        ship.setRegistrationNumber(shipDTO.registrationNumber());
        ship.setYearBuilt(shipDTO.yearBuilt());
        ship.setStatus(shipDTO.status());
        Ship savedShip = shipRepository.save(ship);
        return convertToDto(savedShip);
    }

    public ShipDTO updateShip(Integer id, ShipDTO shipDTO) {
        Ship updatedShip = shipRepository.findById(id)
                .map(ship -> {
                    ship.setName(shipDTO.name());
                    ship.setCompany(shipDTO.company());
                    ship.setCapacity(shipDTO.capacity());
                    ship.setRegistrationNumber(shipDTO.registrationNumber());
                    ship.setYearBuilt(shipDTO.yearBuilt());
                    ship.setStatus(shipDTO.status());
                    return shipRepository.save(ship);
                })
                .orElseThrow(() -> new RuntimeException("Ship not found with ID: " + id));
        return convertToDto(updatedShip);
    }

    public void deleteShip(Integer id) {
        shipRepository.deleteById(id);
    }

    private ShipDTO convertToDto(Ship ship) {
        return new ShipDTO(
                ship.getId(),
                ship.getName(),
                ship.getCompany(),
                ship.getCapacity(),
                ship.getRegistrationNumber(),
                ship.getYearBuilt(),
                ship.getStatus()
        );
    }

    public long getTotalShips() {
        return shipRepository.countAllShips();

    }
}
