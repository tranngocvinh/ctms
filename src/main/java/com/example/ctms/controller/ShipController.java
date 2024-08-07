package com.example.ctms.controller;

import com.example.ctms.dto.ShipDTO;
import com.example.ctms.entity.Ship;
import com.example.ctms.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ships")
public class ShipController {
    @Autowired
    private ShipService shipService;

    @GetMapping
    public List<ShipDTO> getAllShips() {
        return shipService.getAllShips();
    }

    @PostMapping
    public ShipDTO addShip(@RequestBody ShipDTO shipDTO) {
        return shipService.addShip(shipDTO);
    }

    @PutMapping("/{id}")
    public ShipDTO updateShip(@PathVariable Integer id, @RequestBody ShipDTO shipDTO) {
        return shipService.updateShip(id, shipDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteShip(@PathVariable Integer id) {
        shipService.deleteShip(id);
    }
}
