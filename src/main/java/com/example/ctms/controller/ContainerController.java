package com.example.ctms.controller;

import com.example.ctms.dto.ContainerDTO;
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

    @GetMapping
    public List<ContainerDTO> getAllContainers() {
        return containerService.getAllContainers();
    }

    @GetMapping("/{id}")
    public ContainerDTO getContainerById(@PathVariable Integer id) {
        return containerService.getContainerById(id);
    }

    @PostMapping
    public ContainerDTO addContainer(@RequestBody ContainerDTO containerDTO) {
        return containerService.addContainer(containerDTO);
    }

    @PutMapping("/{id}")
    public ContainerDTO updateContainer(@PathVariable Integer id, @RequestBody ContainerDTO containerDTO) {
        return containerService.updateContainer(id, containerDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContainer(@PathVariable Integer id) {
        containerService.deleteContainer(id);
        return ResponseEntity.ok().build();
    }
}
