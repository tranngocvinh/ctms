package com.ctms.service;

import com.ctms.dto.SIDTO;
import com.ctms.entity.CargoType;
import com.ctms.entity.Customer;
import com.ctms.entity.EmptyContainer;
import com.ctms.entity.SI;
import com.ctms.mapper.SIDTOMapper;
import com.ctms.repository.CargoTypeRepository;
import com.ctms.repository.EmptyContainerRepository;
import com.ctms.repository.SIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired
    private CustomerService customerService;

    public List<SIDTO> getAllSIsByRole() {

        Customer customer = customerService.getCurrentCustomer();
        if (customer.getRoles().stream().anyMatch(auth -> auth.equals("MANAGER"))) {
            return siRepository.findAll().stream().map(sidtoMapper).toList();
        }
        return emptyContainerRepository.findByCustomerIdAndIsApproved(customer.getId(), 1)
                .stream()
                .flatMap(emptyContainer ->
                        siRepository.findByEmptyContainerId(emptyContainer.getId())
                                .stream()
                                .map(sidtoMapper) // Assuming `sidtoMapper` is a method reference or function to convert to `SIDTO`
                )
                .collect(Collectors.toList());
    }

    public List<SIDTO> getAllSIs() {
        return siRepository.findAll().stream().map(sidtoMapper).toList()  ;
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
        emptyContainer.setSi(true);
        emptyContainerRepository.save(emptyContainer);
        return siRepository.save(si);
    }

    public SI updateSI(Integer id, SIDTO siDTO) {
        SI si = siRepository.findByEmptyContainerId(id)
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

    public List<CargoType> getAllCargo() {
        return cargoTypeRepository.findAll();
    }
}
