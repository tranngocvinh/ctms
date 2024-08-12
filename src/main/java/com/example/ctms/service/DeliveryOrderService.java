package com.example.ctms.service;

import com.example.ctms.dto.DeliveryOrderDTO;
import com.example.ctms.entity.*;
import com.example.ctms.mapper.DeliveryOrderDTOMapper;
import com.example.ctms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeliveryOrderService {
    private final DeliveryOrderRepository deliveryOrderRepository;
    private final ShipScheduleRepository shipScheduleRepository ;
    private final CustomerRepository customerRepository;
    private final ScheduleRepository scheduleRepository;
    private final ContainerRepository containerRepository;
    private final ShipRepository shipRepository;
    private DeliveryOrderDTOMapper deliveryOrderDTOMapper;

    @Autowired
    public DeliveryOrderService(DeliveryOrderRepository deliveryOrderRepository, ShipScheduleRepository shipScheduleRepository, CustomerRepository customerRepository, ScheduleRepository scheduleRepository, DeliveryOrderDTOMapper deliveryOrderDTOMapper, ContainerRepository containerRepository, ShipRepository shipRepository) {
        this.deliveryOrderRepository = deliveryOrderRepository;
        this.shipScheduleRepository = shipScheduleRepository;
        this.customerRepository = customerRepository;
        this.scheduleRepository = scheduleRepository;
        this.deliveryOrderDTOMapper = deliveryOrderDTOMapper;
        this.containerRepository = containerRepository;
        this.shipRepository = shipRepository;
    }

    public List<DeliveryOrderDTO> getAllDeliveryOrders() {
        return deliveryOrderRepository.findAll().stream()
                .map(deliveryOrderDTOMapper)
                .toList();
    }

    public DeliveryOrderDTO getDeliveryOrderById(Integer id) {
      return deliveryOrderRepository.findById(id).map(deliveryOrderDTOMapper)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));

    }

    public void addDeliveryOrder(DeliveryOrderDTO deliveryOrderDTO) {

        Customer customer = customerRepository.findById(deliveryOrderDTO.customerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Schedule schedule = scheduleRepository.findById(deliveryOrderDTO.scheduleId())
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        DeliveryOrder deliveryOrder = new DeliveryOrder(
                deliveryOrderDTO.orderNumber(),
                customer,
                schedule,
                deliveryOrderDTO.orderDate(),
                deliveryOrderDTO.deliveryDate(),
                deliveryOrderDTO.totalAmount(),
                deliveryOrderDTO.status(),
                deliveryOrderDTO.notes()
        );
        deliveryOrderRepository.save(deliveryOrder);

        // Iterate over the shipScheduleContainerMap
        for (Map.Entry<Long, List<String>> entry : deliveryOrderDTO.shipScheduleContainerMap().entrySet()) {
            Long shipScheduleId = entry.getKey();
            List<String> containerCodes = entry.getValue();

            // Find the corresponding ShipSchedule
            ShipSchedule shipSchedule = shipScheduleRepository.findById(shipScheduleId)
                    .orElseThrow(() -> new RuntimeException("ShipSchedule not found"));

            // Assign containers to the ShipSchedule
            for (String containerCode : containerCodes) {
                Container container = containerRepository.findByContainerCode(containerCode)
                        .orElseThrow(() -> new RuntimeException("Container not found"));

                // Add the container to the ShipSchedule's container list
                shipSchedule.getContainers().add(container);

                // Set the relationship and status on the container
                container.setDeliveryOrder(deliveryOrder);
                container.setHasGoods(true);
                container.setStatus("In Transit");

                // Ensure the bi-directional relationship is maintained
                container.setShipSchedule(shipSchedule);

                // Save the container (and ShipSchedule if necessary)
                containerRepository.save(container);
            }
// If needed, save the ShipSchedule after adding all containers (though it's often handled by cascading)
            shipScheduleRepository.save(shipSchedule);
        }

    }


    public void updateDeliveryOrder(Integer id, DeliveryOrderDTO deliveryOrderDTO) {
        DeliveryOrder order = deliveryOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));

        // Cập nhật các thuộc tính
        order.setOrderNumber(deliveryOrderDTO.orderNumber());
        // Các thuộc tính khác
        order.setDeliveryDate(deliveryOrderDTO.deliveryDate());
        order.setOrderDate(deliveryOrderDTO.orderDate());
        order.setNotes(deliveryOrderDTO.notes());
        order.setStatus(deliveryOrderDTO.status());
        order.setTotalAmount(deliveryOrderDTO.totalAmount());
         deliveryOrderRepository.save(order);
    }

    public void deleteDeliveryOrder(Integer id) {
        DeliveryOrder order = deliveryOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));
        deliveryOrderRepository.delete(order);
    }


}
