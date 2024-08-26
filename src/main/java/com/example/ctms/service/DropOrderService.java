package com.example.ctms.service;

import com.example.ctms.dto.DropOrderDTO;
import com.example.ctms.entity.Customer;
import com.example.ctms.entity.DeliveryOrder;
import com.example.ctms.entity.DropOrder;
import com.example.ctms.entity.SI;
import com.example.ctms.mapper.DropOrderDTOMapper;
import com.example.ctms.repository.DropOrderRepository;
import com.example.ctms.repository.EmptyContainerRepository;
import com.example.ctms.repository.SIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DropOrderService {

    @Autowired
    private DropOrderRepository dropOrderRepository;

    @Autowired
    private SIRepository siRepository;

    @Autowired
    private DropOrderDTOMapper dropOrderDTOMapper;

    private static final double DET_FEE_PER_DAY = 400000; // Example fee per day
    @Autowired
    private CustomerService customerService;
    @Autowired
    private EmptyContainerRepository emptyContainerRepository;

    public List<DropOrderDTO> getAllDropOrders() {
        Customer customer = customerService.getCurrentCustomer();
        if (customer.getRoles().stream().anyMatch(auth -> auth.equals("MANAGER") || auth.equals("STAFF"))) {
            return dropOrderRepository.findAll().stream().map(dropOrderDTOMapper).toList();
        }
        return emptyContainerRepository.findByCustomerIdAndIsApproved(customer.getId(), 1)
                .stream()
                .flatMap(emptyContainer ->
                        siRepository.findByEmptyContainerId(emptyContainer.getId())
                                .stream()
                                .flatMap(si ->
                                        dropOrderRepository.findDropOrderBySiId(si.getId())
                                                .stream())
                                .map(dropOrderDTOMapper) // Assuming `sidtoMapper` is a method reference or function to convert to `SIDTO`
                )
                .collect(Collectors.toList());

        }

    public Optional<DropOrderDTO> getDropOrderById(Integer id) {
        return dropOrderRepository.findById(id).stream().map(dropOrderDTOMapper).findFirst();
    }

    public DropOrder createDropOrder(DropOrderDTO dropOrderDTO) {
        double detFee = 0;
        SI si = siRepository.findById(dropOrderDTO.si()).
                orElseThrow(() -> new RuntimeException("si not found"));
        LocalDateTime emptyContainerApprovalDate = si.getEmptyContainer().getApprovalDate();

        // Calculate DET fee if drop date is more than 3 days after the empty container approval date
        if (dropOrderDTO.dropDate() != null && emptyContainerApprovalDate != null) {
            long daysBetween = Duration.between(emptyContainerApprovalDate, dropOrderDTO.dropDate()).toDays();
            if (daysBetween > 3) {
                detFee = Math.floor((daysBetween - 3) * DET_FEE_PER_DAY);

            }
        }
        DropOrder dropOrder = new DropOrder(si,dropOrderDTO.dropDate(),dropOrderDTO.dropLocation(), dropOrderDTO.status(),detFee);


        return dropOrderRepository.save(dropOrder);
    }

    public DropOrder updateDropOrder(Integer id, DropOrderDTO dropOrderDTO) {
        double detFee = 0;

        DropOrder dropOrder = dropOrderRepository.findDropOrderBySiId(id)
                .orElseThrow(() -> new RuntimeException("DropOrder not found"));
        SI si = siRepository.findById(id).
                orElseThrow(() -> new RuntimeException("si not found"));
        LocalDateTime emptyContainerApprovalDate = si.getEmptyContainer().getApprovalDate();

        // Calculate DET fee if drop date is more than 3 days after the empty container approval date
        if (dropOrderDTO.dropDate() != null && emptyContainerApprovalDate != null) {
            long daysBetween = Duration.between(emptyContainerApprovalDate, dropOrderDTO.dropDate()).toDays();
            if (daysBetween > 3) {
                detFee = (daysBetween - 3) * DET_FEE_PER_DAY;

            }
        }
        dropOrder.setDropDate(dropOrderDTO.dropDate());
        dropOrder.setDropLocation(dropOrderDTO.dropLocation());
        dropOrder.setStatus(dropOrderDTO.status());
        dropOrder.setDetFee(detFee);
        dropOrder.setUpdatedAt(LocalDateTime.now());

        return dropOrderRepository.save(dropOrder);
    }

    public void deleteDropOrder(Integer id) {
        dropOrderRepository.deleteById(id);
    }

    public Long getTotalDetFee() {
        return dropOrderRepository.sumAllDetFee();
    }

    public Map<Integer, Double> getDetFeeCountByMonth() {
        List<Map<String, Object>> results = dropOrderRepository.sumDetFeeByMonth();
        Map<Integer, Double> totalAmountByMonth = new HashMap<>();

        for (Map<String, Object> result : results) {
            Integer month = (Integer) result.get("month");
            Double totalAmount = (Double) result.get("totalDetFee");
            totalAmountByMonth.put(month, totalAmount);
        }
        return totalAmountByMonth;
    }

    public void updatePay(Integer id) {
        DropOrder order = dropOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("dropOrder not found with id " + id));

        order.setIsPay(1);
        dropOrderRepository.save(order);
    }
}
