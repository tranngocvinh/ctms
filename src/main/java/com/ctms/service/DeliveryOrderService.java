package com.ctms.service;

import com.ctms.dto.DeliveryOrderDTO;
import com.ctms.entity.*;
import com.ctms.mapper.DeliveryOrderDTOMapper;
import com.ctms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
    private final CustomerService customerService;
    private final PortLocationRepository portLocationRepository;
    private final EmptyContainerRepository emptyContainerRepository;
    private final EmptyContainerDetailRepository emptyContainerDetailRepository;
    private DeliveryOrderDTOMapper deliveryOrderDTOMapper;

    @Autowired
    public DeliveryOrderService(DeliveryOrderRepository deliveryOrderRepository, ShipScheduleRepository shipScheduleRepository, CustomerRepository customerRepository, ScheduleRepository scheduleRepository, DeliveryOrderDTOMapper deliveryOrderDTOMapper, ContainerRepository containerRepository, ShipRepository shipRepository, CustomerService customerService, PortLocationRepository portLocationRepository, EmptyContainerRepository emptyContainerRepository, EmptyContainerDetailRepository emptyContainerDetailRepository) {
        this.deliveryOrderRepository = deliveryOrderRepository;
        this.shipScheduleRepository = shipScheduleRepository;
        this.customerRepository = customerRepository;
        this.scheduleRepository = scheduleRepository;
        this.deliveryOrderDTOMapper = deliveryOrderDTOMapper;
        this.containerRepository = containerRepository;
        this.shipRepository = shipRepository;
        this.customerService = customerService;
        this.portLocationRepository = portLocationRepository;
        this.emptyContainerRepository = emptyContainerRepository;
        this.emptyContainerDetailRepository = emptyContainerDetailRepository;
    }

    public List<DeliveryOrderDTO> getAllDeliveryOrders() {
        Customer customer = customerService.getCurrentCustomer() ;
        if (customer.getRoles().stream().anyMatch(auth -> auth.equals("MANAGER") || auth.equals("STAFF")) ) {
            return deliveryOrderRepository.findAll().stream()
                    .map(deliveryOrderDTOMapper)
                    .toList();
        }
        return deliveryOrderRepository.findByCustomerId(customer.getId())
                .stream()
                .map(deliveryOrderDTOMapper)
                .toList() ;
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
        order.setDeliveryDate(deliveryOrderDTO.deliveryDate());
        order.setOrderDate(deliveryOrderDTO.orderDate());
        order.setNotes(deliveryOrderDTO.notes());
        order.setStatus(deliveryOrderDTO.status());
        order.setTotalAmount(deliveryOrderDTO.totalAmount());

        for (Map.Entry<Long, List<String>> entry : deliveryOrderDTO.shipScheduleContainerMap().entrySet()) {
            Long shipScheduleId = entry.getKey();
            List<String> containerCodes = entry.getValue();

            // Find the corresponding ShipSchedule
            ShipSchedule shipSchedule = shipScheduleRepository.findById(shipScheduleId)
                    .orElseThrow(() -> new RuntimeException("ShipSchedule not found"));

            // Tạo danh sách container hiện tại để so sánh
            List<Container> currentContainers = new ArrayList<>(shipSchedule.getContainers());

            // Xóa các container không còn được chọn
            for (Container container : currentContainers) {
                if (!containerCodes.contains(container.getContainerCode())) {
                    shipSchedule.getContainers().remove(container);
                    container.setShipSchedule(null);
                    container.setDeliveryOrder(null);
                    container.setHasGoods(true);
                    container.setStatus("In Port");
                    container.setIsRepair(0);
                    containerRepository.save(container);
                }
            }

            // Thêm hoặc giữ lại các container được chọn
            for (String containerCode : containerCodes) {
                Container container = containerRepository.findByContainerCode(containerCode)
                        .orElseThrow(() -> new RuntimeException("Container not found"));

                if (!shipSchedule.getContainers().contains(container)) {
                    shipSchedule.getContainers().add(container);
                }

                // Cập nhật mối quan hệ và trạng thái của container
                container.setDeliveryOrder(order);
                container.setHasGoods(true);
                container.setStatus("In Transit");
                container.setShipSchedule(shipSchedule);

                containerRepository.save(container);
            }

            shipScheduleRepository.save(shipSchedule);
        }

        deliveryOrderRepository.save(order);
    }


    public void updatePayToDelivered(Integer id,DeliveryOrderDTO deliveryOrderDTO) {
        DeliveryOrder order = deliveryOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));

        order.setIsPay(2);
        order.setOrderNumber(deliveryOrderDTO.orderNumber());
        order.setDeliveryDate(deliveryOrderDTO.deliveryDate());
        order.setOrderDate(deliveryOrderDTO.orderDate());
        order.setNotes(deliveryOrderDTO.notes());
        order.setStatus(deliveryOrderDTO.status());
        order.setTotalAmount(deliveryOrderDTO.totalAmount());
        // Find the Schedule
        Schedule schedule = scheduleRepository.findById(deliveryOrderDTO.scheduleId())
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        // Get the last waypoint from the schedule's waypoints list
        List<Waypoint> waypoints = schedule.getRoute().getWaypoints();
        if (waypoints.isEmpty()) {
            throw new RuntimeException("No waypoints found for schedule " + schedule.getId());
        }
        Waypoint lastWaypoint = waypoints.get(waypoints.size() - 1); // Get the last waypoint
        PortLocation portLocation = portLocationRepository.findByPortName(lastWaypoint.getPortName()).orElseThrow(() -> new RuntimeException("Port not found"));
        for (Map.Entry<Long, List<String>> entry : deliveryOrderDTO.shipScheduleContainerMap().entrySet()) {
            Long shipScheduleId = entry.getKey();
            List<String> containerCodes = entry.getValue();

            // Find the corresponding ShipSchedule
            ShipSchedule shipSchedule = shipScheduleRepository.findById(shipScheduleId)
                    .orElseThrow(() -> new RuntimeException("ShipSchedule not found"));


            // Thêm hoặc giữ lại các container được chọn

            for (String containerCode : containerCodes) {
                Container container = containerRepository.findByContainerCode(containerCode)
                        .orElseThrow(() -> new RuntimeException("Container not found"));

                // Cập nhật mối quan hệ và trạng thái của container
                container.setDeliveryOrder(null);
                container.setHasGoods(false);
                container.setStatus("In Port");
                container.setShipSchedule(shipSchedule);
                container.setPortLocation(portLocation);
                container.setCustomer(null);
                container.setEmptyContainerDetail(null);
                EmptyContainerDetail emptyContainerDetail = emptyContainerDetailRepository.findEmptyContainerDetailByContainer_ContainerCode(containerCode)
                        .orElseThrow(() -> new RuntimeException("Empty container detail not found"));
                EmptyContainer emptyContainer = emptyContainerRepository.findById(emptyContainerDetail.getEmptyContainer().getId())
                        .orElseThrow(() -> new RuntimeException("Empty container not found"));
                emptyContainer.setCustomer(null);
                emptyContainerDetailRepository.deleteById(emptyContainerDetail.getId());
                containerRepository.save(container);
            }

            shipScheduleRepository.save(shipSchedule);
        }
        deliveryOrderRepository.save(order);
    }


    public void deleteDeliveryOrder(Integer id) {
        DeliveryOrder order = deliveryOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));
        deliveryOrderRepository.delete(order);
    }


    public void updatePay(Integer id) {
        DeliveryOrder order = deliveryOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));

        order.setIsPay(1);
        deliveryOrderRepository.save(order);
    }


    public Double getTotalPaidRepairCost() {
        return deliveryOrderRepository.sumPaiDeliveryCost();
    }

    public Map<Integer, Double> getTotalAmountByMonth() {
        List<Map<String, Object>> results = deliveryOrderRepository.sumTotalAmountByMonth();
        Map<Integer, Double> totalAmountByMonth = new HashMap<>();

        for (Map<String, Object> result : results) {
            Integer month = (Integer) result.get("month");
            Double totalAmount = (Double) result.get("totalAmount");
            totalAmountByMonth.put(month, totalAmount);
        }

        return totalAmountByMonth;
    }
}
