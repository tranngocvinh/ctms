package com.example.ctms.service;

import com.example.ctms.dto.SIDTO;
import com.example.ctms.entity.CargoType;
import com.example.ctms.entity.EmptyContainer;
import com.example.ctms.entity.SI;
import com.example.ctms.mapper.SIDTOMapper;
import com.example.ctms.repository.CargoTypeRepository;
import com.example.ctms.repository.EmptyContainerRepository;
import com.example.ctms.repository.SIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SIService {

    @Autowired
    private SIRepository siRepository;

    @Autowired
    private EmptyContainerRepository emptyContainerRepository;

    @Autowired
    private CargoTypeRepository cargoTypeRepository;

    @Autowired
    private SIDTOMapper sidtoMapper ;

    public List<SIDTO> getAllSIs() {
        return siRepository.findAll().stream().map(sidtoMapper).toList();
    }

    public Optional<SIDTO> getSIById(Integer id) {
        return siRepository.findById(id).stream().map(sidtoMapper).findFirst();
    }

    public SI createSI(SIDTO siDTO) {
        EmptyContainer emptyContainer = emptyContainerRepository.findById(siDTO.getEmptyContainerId())
                .orElseThrow(() -> new RuntimeException("EmptyContainer not found"));

        CargoType cargoType = cargoTypeRepository.findById(siDTO.getCargoTypeId())
                .orElseThrow(() -> new RuntimeException("CargoType not found"));

        SI si = new SI();
        si.setEmptyContainer(emptyContainer);
        si.setCargoType(cargoType);
        si.setCargoWeight(siDTO.getCargoWeight());
        si.setCargoVolume(siDTO.getCargoVolume());

        return siRepository.save(si);
    }

    public SI updateSI(Integer id, SIDTO siDTO) {
        SI si = siRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SI not found"));

        EmptyContainer emptyContainer = emptyContainerRepository.findById(siDTO.getEmptyContainerId())
                .orElseThrow(() -> new RuntimeException("EmptyContainer not found"));

        CargoType cargoType = cargoTypeRepository.findById(siDTO.getCargoTypeId())
                .orElseThrow(() -> new RuntimeException("CargoType not found"));

        si.setEmptyContainer(emptyContainer);
        si.setCargoType(cargoType);
        si.setCargoWeight(siDTO.getCargoWeight());
        si.setCargoVolume(siDTO.getCargoVolume());

        return siRepository.save(si);
    }

    public void deleteSI(Integer id) {
        siRepository.deleteById(id);
    }
}
