package com.example.ctms.service;

import com.example.ctms.dto.RepairDTO;
import com.example.ctms.entity.*;
import com.example.ctms.repository.ContainerCleaningRepository;
import com.example.ctms.repository.ContainerRepository;
import com.example.ctms.repository.ContainerSupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContainerCleaningService {

    @Autowired
    private ContainerCleaningRepository containerCleaningRepository;

    @Autowired
    private ContainerRepository containerRepository;

    @Autowired
    private ContainerSupplierRepository repairSupplierRepository;


    @Autowired
    private CustomerService customerService;

    public Cleaning createcLeaning(Cleaning cleaning) {
        Container container = containerRepository.findById(cleaning.getContainer().getContainerCode())
                .orElseThrow(() -> new RuntimeException("Container not found with id " + cleaning.getContainer().getContainerCode()));

        ContainerSupplier supplier = repairSupplierRepository.findById(cleaning.getSupplier().getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found with id " + cleaning.getSupplier().getSupplierId()));


        ContainerSupplier containerSupplier = null;
        if (cleaning.getSupplier().getSupplierId() != null) {
            containerSupplier = repairSupplierRepository.findById(cleaning.getSupplier().getSupplierId())
                    .orElseThrow(() -> new RuntimeException("ContainerSupplier not found"));
        }
        cleaning.setContainer(container);
        cleaning.setSupplier(supplier);
        cleaning.setDescription(cleaning.getDescription());
        cleaning.setIsCleaning(1);
        cleaning.setIsPayment(0);
        // Update container status
        container.setStatus("Under Maintenance");
        container.setHasGoods(false);
        container.setContainerSupplier(containerSupplier);
        container.setIsRepair(1);
        return containerCleaningRepository.save(cleaning);
    }


}
