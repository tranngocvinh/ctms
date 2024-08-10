package com.example.ctms.service;

import com.example.ctms.dto.DeliveryOrderDTO;
import com.example.ctms.entity.*;
import com.example.ctms.mapper.DeliveryOrderDTOMapper;
import com.example.ctms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryOrderService {
    private final DeliveryOrderRepository deliveryOrderRepository;
    private final CustomerRepository customerRepository;
    private final ScheduleRepository scheduleRepository;
    private final ContainerRepository containerRepository;
    private final ShipRepository shipRepository;
    private DeliveryOrderDTOMapper deliveryOrderDTOMapper;

    @Autowired
    public DeliveryOrderService(DeliveryOrderRepository deliveryOrderRepository, CustomerRepository customerRepository, ScheduleRepository scheduleRepository, DeliveryOrderDTOMapper deliveryOrderDTOMapper, ContainerRepository containerRepository, ShipRepository shipRepository) {
        this.deliveryOrderRepository = deliveryOrderRepository;
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

        Customer customer  = customerRepository.findById(deliveryOrderDTO.customerId())
                .orElseThrow(() -> new RuntimeException("ko tim thay customer"));
        Schedule schedule = scheduleRepository.findById(deliveryOrderDTO.scheduleId())
                .orElseThrow(() -> new RuntimeException("Schedule not found")) ;
        DeliveryOrder deliveryOrder = new DeliveryOrder(
                deliveryOrderDTO.orderNumber(),
                customer,
                schedule,
                deliveryOrderDTO.orderDate(),
                deliveryOrderDTO.deliveryDate(),
                deliveryOrderDTO.totalAmount(),
                deliveryOrderDTO.status(),
                deliveryOrderDTO.notes()
        ) ;

        Container container = null ;
        for (int i = 0 ; i < deliveryOrderDTO.containerCode().size() ; i++){
             container = containerRepository.findByContainerCode(deliveryOrderDTO.containerCode().get(i))
                    .orElseThrow(() -> new RuntimeException("container not found"));

            container.setDeliveryOrder(deliveryOrder);
            container.setHasGoods(true);
            container.setStatus("In Transit");


        }
         deliveryOrderRepository.save(deliveryOrder) ;
        containerRepository.save(container);

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
