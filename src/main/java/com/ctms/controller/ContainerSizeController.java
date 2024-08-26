package com.ctms.controller;

import com.ctms.dto.ContainerSizeDTO;
import com.ctms.service.ContainerSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/containers")
public class ContainerSizeController {
    @Autowired
    private ContainerSizeService containerSizeService;

    @GetMapping("/sizes")
    public List<ContainerSizeDTO> getAllSizes() {
        return containerSizeService.getAllSizes();
    }

    @GetMapping("/sizes/type/{typeId}")
    public List<ContainerSizeDTO> getSizesByType(@PathVariable Integer typeId) {
        return containerSizeService.getSizesByType(typeId);
    }

    @PostMapping("/sizes")
    public ContainerSizeDTO addSize(@RequestBody ContainerSizeDTO sizeDTO) {
        return containerSizeService.addSize(sizeDTO);
    }

    @PutMapping("/sizes/{id}")
    public ContainerSizeDTO updateSize(@PathVariable Integer id, @RequestBody ContainerSizeDTO sizeDTO) {
        return containerSizeService.updateSize(id, sizeDTO);
    }

    @DeleteMapping("/sizes/{id}")
    public void deleteSize(@PathVariable Integer id) {
        containerSizeService.deleteSize(id);
    }

    @GetMapping("/initialize")
    public void initializeContainerTypes() {
        containerSizeService.initializeContainerTypes();
    }
}
