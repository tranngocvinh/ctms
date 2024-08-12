package com.example.ctms.service;

import com.example.ctms.dto.DropOrderDTO;
import com.example.ctms.entity.DropOrder;
import com.example.ctms.entity.SI;
import com.example.ctms.mapper.DropOrderDTOMapper;
import com.example.ctms.repository.DropOrderRepository;
import com.example.ctms.repository.SIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DropOrderService {

    @Autowired
    private DropOrderRepository dropOrderRepository;

    @Autowired
    private SIRepository siRepository;

    @Autowired
    private DropOrderDTOMapper dropOrderDTOMapper;

    private static final double DET_FEE_PER_DAY = 400000; // Example fee per day

    public List<DropOrderDTO> getAllDropOrders() {
        return dropOrderRepository.findAll().stream().map(dropOrderDTOMapper).toList();
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
                detFee = (daysBetween - 3) * DET_FEE_PER_DAY;
                
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
}
