package com.example.ctms.controller;

import com.example.ctms.dto.ContainerDTO;
import com.example.ctms.dto.EmptyContainerRequestDto;
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
    public ResponseEntity<List<ContainerDTO>> getAllContainers() {
        return ResponseEntity.ok(containerService.getAllContainers());
    }

    @GetMapping("/{containerCode}")
    public ResponseEntity<ContainerDTO> getContainerById(@PathVariable String containerCode) {
        return ResponseEntity.ok(containerService.getContainerById(containerCode));
    }

    @PostMapping
    public ResponseEntity<ContainerDTO> addContainer(@RequestBody ContainerDTO containerDTO) {
        return ResponseEntity.ok(containerService.addContainer(containerDTO));
    }

//    @PutMapping("/{containerCode}")
//    public ResponseEntity<ContainerDTO> updateContainer(@PathVariable String containerCode, @RequestBody ContainerDTO containerDTO) {
//        return ResponseEntity.ok(containerService.updateContainer(containerCode, containerDTO));
//    }

    @DeleteMapping("/{containerCode}")
    public ResponseEntity<Void> deleteContainer(@PathVariable String containerCode) {
        containerService.deleteContainer(containerCode);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/allocate/ship")
    public ResponseEntity<Void> allocateEmptyContainersToShip(@RequestBody EmptyContainerRequestDto request) {
        containerService.allocateEmptyContainersToShip(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/allocate/port")
    public ResponseEntity<Void> allocateEmptyContainersToPort(@RequestParam int numberOfContainers, @RequestParam String portName) {
        containerService.allocateEmptyContainersToPort(numberOfContainers, portName);
        return ResponseEntity.ok().build();
    }
}
