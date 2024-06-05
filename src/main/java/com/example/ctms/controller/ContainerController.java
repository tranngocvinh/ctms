package com.example.ctms.controller;

import com.example.ctms.dto.ContainerSizeDTO;
import com.example.ctms.entity.ContainerSize;
import com.example.ctms.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/containers")
public class ContainerController {
    @Autowired
    private ContainerService containerService;

    @GetMapping("/sizes")
    public List<ContainerSizeDTO> getAllSizes() {
        return containerService.getAllSizes();
    }

    @GetMapping("/sizes/type/{typeId}")
    public List<ContainerSize> getSizesByType(@PathVariable Integer typeId) {
        return containerService.getSizesByType(typeId);
    }

    @PostMapping("/sizes")
    public ContainerSize addSize(@RequestBody ContainerSizeDTO sizeDTO) {
        return containerService.addSize(sizeDTO);
    }

    @PutMapping("/sizes/{id}")
    public ContainerSize updateSize(@PathVariable Integer id, @RequestBody ContainerSizeDTO sizeDTO) {
        return containerService.updateSize(id, sizeDTO);
    }

    @DeleteMapping("/sizes/{id}")
    public ResponseEntity<?> deleteSize(@PathVariable Integer id) {
        containerService.deleteSize(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/initialize")
    public void initializeContainerTypes() {

         containerService.initializeContainerTypes();
    }
}
