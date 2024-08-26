package com.ctms.controller;

import com.ctms.entity.CargoType;
import com.ctms.service.CargoTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cargo-types")
public class CargoTypeController {

    @Autowired
    private CargoTypeService cargoTypeService;

    @GetMapping
    public List<CargoType> getAllCargoTypes() {
        return cargoTypeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargoType> getCargoTypeById(@PathVariable Integer id) {
        CargoType cargoType = cargoTypeService.findById(id);
        if (cargoType != null) {
            return ResponseEntity.ok(cargoType);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public CargoType createCargoType(@RequestBody CargoType cargoType) {
        return cargoTypeService.save(cargoType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CargoType> updateCargoType(@PathVariable Integer id, @RequestBody CargoType cargoTypeDetails) {
        CargoType cargoType = cargoTypeService.findById(id);
        if (cargoType != null) {
            cargoType.setName(cargoTypeDetails.getName());
            return ResponseEntity.ok(cargoTypeService.save(cargoType));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCargoType(@PathVariable Integer id) {
        CargoType cargoType = cargoTypeService.findById(id);
        if (cargoType != null) {
            cargoTypeService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
