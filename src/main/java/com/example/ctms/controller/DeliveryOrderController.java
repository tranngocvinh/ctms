package com.example.ctms.controller;

import com.example.ctms.dto.DeliveryOrderDTO;
import com.example.ctms.entity.DeliveryOrder;
import com.example.ctms.service.DeliveryOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery-orders")
public class DeliveryOrderController {
    private final DeliveryOrderService deliveryOrderService;

    @Autowired
    public DeliveryOrderController(DeliveryOrderService deliveryOrderService) {
        this.deliveryOrderService = deliveryOrderService;
    }

    @GetMapping
    public ResponseEntity<List<DeliveryOrderDTO>> getAllDeliveryOrders() {
        return ResponseEntity.ok(deliveryOrderService.getAllDeliveryOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryOrderDTO> getDeliveryOrderById(@PathVariable Integer id) {
        return ResponseEntity.ok(deliveryOrderService.getDeliveryOrderById(id));
    }

    @PostMapping
    public void addDeliveryOrder(@RequestBody DeliveryOrderDTO deliveryOrderDTO) {
         deliveryOrderService.addDeliveryOrder(deliveryOrderDTO);
    }

    @PutMapping("/{id}")
    public void updateDeliveryOrder(@PathVariable Integer id, @RequestBody DeliveryOrderDTO deliveryOrderDTO) {
       deliveryOrderService.updateDeliveryOrder(id, deliveryOrderDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeliveryOrder(@PathVariable Integer id) {
        deliveryOrderService.deleteDeliveryOrder(id);
        return ResponseEntity.noContent().build();
    }
}