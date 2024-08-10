package com.example.ctms.service;

import com.example.ctms.entity.CargoType;
import com.example.ctms.repository.CargoTypeRepository;
import com.example.ctms.service.CargoTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CargoTypeServiceImpl implements CargoTypeService {

    @Autowired
    private CargoTypeRepository cargoTypeRepository;

    @Override
    public List<CargoType> findAll() {
        return cargoTypeRepository.findAll();
    }

    @Override
    public CargoType findById(Integer id) {
        Optional<CargoType> optionalCargoType = cargoTypeRepository.findById(id);
        return optionalCargoType.orElse(null);
    }

    @Override
    public CargoType save(CargoType cargoType) {
        return cargoTypeRepository.save(cargoType);
    }

    @Override
    public void deleteById(Integer id) {
        cargoTypeRepository.deleteById(id);
    }
}
