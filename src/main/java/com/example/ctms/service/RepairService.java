package com.example.ctms.service;

import com.example.ctms.dto.RepairDTO;
import com.example.ctms.entity.Container;
import com.example.ctms.entity.Customer;
import com.example.ctms.entity.Repair;
import com.example.ctms.entity.ContainerSupplier;
import com.example.ctms.mapper.RepairDTOMapper;
import com.example.ctms.repository.ContainerRepository;
import com.example.ctms.repository.RepairRepository;
import com.example.ctms.repository.ContainerSupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.registry.infomodel.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RepairService {

    @Autowired
    private RepairRepository repairRepository;

    @Autowired
    private ContainerRepository containerRepository;

    @Autowired
    private ContainerSupplierRepository repairSupplierRepository;

    @Autowired
    private RepairDTOMapper repairDTOMapper ;
    @Autowired
    private CustomerService customerService;

    public Repair createRepair(Repair repair) {
        Container container = containerRepository.findById(repair.getContainer().getContainerCode())
                .orElseThrow(() -> new RuntimeException("Container not found with id " + repair.getContainer().getContainerCode()));

        ContainerSupplier supplier = repairSupplierRepository.findById(repair.getSupplier().getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found with id " + repair.getSupplier().getSupplierId()));


            ContainerSupplier containerSupplier = null;
        if (repair.getSupplier().getSupplierId() != null) {
            containerSupplier = repairSupplierRepository.findById(repair.getSupplier().getSupplierId())
                    .orElseThrow(() -> new RuntimeException("ContainerSupplier not found"));
        }
        repair.setContainer(container);
        repair.setSupplier(supplier);
        repair.setRepairCost(repair.getRepairCost());
        repair.setRepairDate(repair.getRepairDate());
        repair.setDescription(repair.getDescription());
        repair.setIsRepair(1);
        repair.setIsPayment(0) ;
        // Update container status
        container.setStatus("Under Maintenance");
        container.setHasGoods(false);
        container.setContainerSupplier(containerSupplier);
        container.setIsRepair(1);
        containerRepository.save(container);
        return repairRepository.save(repair);
    }

    public List<RepairDTO> getAllRepair() {
        Customer currentUser = customerService.getCurrentCustomer(); // Fetch current user
        List<Repair> repairs = null;

        if (currentUser.getRoles().stream().anyMatch(auth ->  auth.equals("STAFF"))) {
            // Admins can see all repairs
            repairs = repairRepository.findAll();
        } else {
            // Regular users can only see repairs for containers they own or manage
            List<String> userContainers = containerRepository.findByCustomerId(currentUser.getId())
                    .stream()
                    .map(Container::getContainerCode)
                    .collect(Collectors.toList());

            repairs = repairRepository.findByContainerContainerCodeIn(userContainers);
        }

        return repairs.stream().map(repairDTOMapper).collect(Collectors.toList());
    }



    public Optional<RepairDTO> getRepairById(Long id) {

        return repairRepository.findById(id).stream().map(repairDTOMapper).findFirst();
    }

    public void updateRepair(Long id, RepairDTO repairDTO) {
        Repair repair = repairRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repair not found with id " + id));

        Container container = containerRepository.findById(repairDTO.containerCode())
                .orElseThrow(() -> new RuntimeException("Container not found with code " + repairDTO.containerCode()));

        ContainerSupplier supplier = repairSupplierRepository.findById(repairDTO.containerSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found with id " + repairDTO.containerSupplierId()));

        repair.setContainer(container);
        repair.setSupplier(supplier);
        repair.setRepairCost(repairDTO.repairCode());
        repair.setRepairDate(repairDTO.repairDate());
        repair.setDescription(repairDTO.description());

         repairRepository.save(repair);
    }

//    public void deleteRepair(Long id) {
//        Repair repair = repairRepository.findById(id).orElseThrow(() -> new RuntimeException("Repair not found with id " + id)); ;
//
//        Container container = repair.getContainer();
//        container.setStatus("In Port");
//        container.setHasGoods(false);
//        container.setIsRepair(0);
//        repairRepository.deleteById(id);
//
//
//    }

    public void repairFinish(Long id) {
        Repair repair = repairRepository.findById(id).orElseThrow(() -> new RuntimeException("Repair not found with id " + id)); ;
        Container container = repair.getContainer();
        container.setStatus("In Port");
        container.setHasGoods(false);
        container.setIsRepair(0);
        repair.setIsRepair(0); ;
        containerRepository.save(container);
    }

    public void handlePayment(Long id) {
        Repair repair = repairRepository.findById(id).orElseThrow(() -> new RuntimeException("Repair not found with id " + id)); ;
        repair.setIsPayment(1); ;
        repairRepository.save(repair);
    }


    public Double getTotalPaidRepairCost() {
        return repairRepository.sumPaidRepairCost();
    }

    public Map<Integer, Double> getRepairCostCountByMonth() {
        List<Map<String, Object>> results = repairRepository.sumRepairCostByMonth();
        Map<Integer, Double> totalAmountByMonth = new HashMap<>();

        for (Map<String, Object> result : results) {
            Integer month = (Integer) result.get("month");
            Double totalAmount = (Double) result.get("totalRepairCost");
            totalAmountByMonth.put(month, totalAmount);
        }
        return totalAmountByMonth;
    }
}
