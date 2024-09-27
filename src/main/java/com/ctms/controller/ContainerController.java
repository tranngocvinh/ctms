package com.ctms.controller;

import com.ctms.service.ContainerService;
import com.ctms.dto.ContainerDTO;
import com.ctms.dto.EmptyContainerDTO;
import com.ctms.dto.EmptyContainerRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@EnableMBeanExport
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

    @PutMapping("/{containerCode}")
    public ResponseEntity<ContainerDTO> updateContainer(@PathVariable String containerCode, @RequestBody ContainerDTO containerDTO) {
        return ResponseEntity.ok(containerService.updateContainer(containerCode, containerDTO));
    }

    @DeleteMapping("/{containerCode}")
    public ResponseEntity<Void> deleteContainer(@PathVariable String containerCode) {
        containerService.deleteContainer(containerCode);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/allocate/ship")
    public List<EmptyContainerDTO> GetAllEmptyContainers() {
        return containerService.getAllEmptyContainer() ;
    }

    @GetMapping("/allocate/ship/isApprove")
    public List<EmptyContainerDTO> GetAllEmptyContainersIsApprove() {
        return containerService.getAllEmptyContainerIsApprove() ;
    }




    @GetMapping("/allocate/ship/{id}")
    public Optional<EmptyContainerDTO> GetAllEmptyContainerById(@PathVariable int id) {
        return containerService.getAllEmptyContainerById(id) ;
    }
    @PostMapping("/allocate/ship")
    public ResponseEntity<Void> allocateEmptyContainersToShip(@RequestBody EmptyContainerRequestDto request) {
        containerService.allocateEmptyContainersToShip(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/allocate/ship/approved/{id}")
    public void isApproved(@PathVariable int id){
         containerService.isApproved(id) ;
    }

    @PutMapping ("/allocate/ship/reject/{id}")
    public void isReject(@PathVariable int id){
        containerService.isReject(id) ;
    }

    @GetMapping("/count")
    public long getTotalContainers() {
        return containerService.getTotalContainers();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ContainerDTO>> searchContainers(@RequestParam String query) {
        List<ContainerDTO> containers = containerService.searchContainersByCode(query);
        return ResponseEntity.ok(containers);
    }

    @GetMapping("/getByPort")
    public ResponseEntity<List<ContainerDTO>> getContainersByPort(@RequestParam(required = false) Integer portId) {
        List<ContainerDTO> containers = containerService.findContainersByPortId(portId);

        return ResponseEntity.ok(containers);
    }

}
