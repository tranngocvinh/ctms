package com.example.ctms.controller;

import com.example.ctms.dto.DropOrderDTO;
import com.example.ctms.entity.DropOrder;
import com.example.ctms.service.DropOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/drop-orders")
public class DropOrderController {

    @Autowired
    private DropOrderService dropOrderService;

    @GetMapping
    public List<DropOrderDTO> getAllDropOrders() {
        return dropOrderService.getAllDropOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DropOrderDTO> getDropOrderById(@PathVariable Integer id) {
        Optional<DropOrderDTO> dropOrder = dropOrderService.getDropOrderById(id);
        return dropOrder.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DropOrder> createDropOrder(@RequestBody DropOrderDTO dropOrderDTO) {
        DropOrder createdDropOrder = dropOrderService.createDropOrder(dropOrderDTO);
        return ResponseEntity.ok(createdDropOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DropOrder> updateDropOrder(@PathVariable Integer id, @RequestBody DropOrderDTO dropOrderDTO) {
        DropOrder updatedDropOrder = dropOrderService.updateDropOrder(id, dropOrderDTO);
        return ResponseEntity.ok(updatedDropOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDropOrder(@PathVariable Integer id) {
        dropOrderService.deleteDropOrder(id);
        return ResponseEntity.noContent().build();
    }
}
