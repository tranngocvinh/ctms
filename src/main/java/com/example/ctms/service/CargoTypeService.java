package com.example.ctms.service;

import com.example.ctms.entity.CargoType;

import java.util.List;

public interface CargoTypeService {
    List<CargoType> findAll();
    CargoType findById(Integer id);
    CargoType save(CargoType cargoType);
    void deleteById(Integer id);
}
