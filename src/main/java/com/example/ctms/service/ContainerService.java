package com.example.ctms.service;

import com.example.ctms.dto.ContainerSizeDTO;
import com.example.ctms.entity.ContainerSize;
import com.example.ctms.entity.ContainerType;
import com.example.ctms.repository.ContainerSizeRepository;
import com.example.ctms.repository.ContainerTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContainerService {
    @Autowired
    private ContainerSizeRepository containerSizeRepository;

    @Autowired
    private ContainerTypeRepository containerTypeRepository;


    public List<ContainerSizeDTO> getAllSizes() {
        List<ContainerSize> containerSizes = containerSizeRepository.findAll();
        return containerSizes.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<ContainerSize> getSizesByType(Integer typeId) {
        return containerSizeRepository.findByContainerTypeId(typeId);
    }

    public ContainerSize addSize(ContainerSizeDTO sizeDTO) {
        ContainerType containerType = containerTypeRepository.findById(sizeDTO.getContainerTypeId())
                .orElseThrow(() -> new RuntimeException("ContainerType not found"));
        ContainerSize size = new ContainerSize();
        size.setLength(sizeDTO.getLength());
        size.setWidth(sizeDTO.getWidth());
        size.setHeight(sizeDTO.getHeight());
        size.setVolume(sizeDTO.getVolume());
        size.setWeight(sizeDTO.getWeight());
        size.setLoadCapacity(sizeDTO.getLoadCapacity());
        size.setMaxLoad(sizeDTO.getMaxLoad());
        size.setContainerType(containerType);
        return containerSizeRepository.save(size);
    }

    public ContainerSize updateSize(Integer id, ContainerSizeDTO sizeDTO) {
        ContainerType containerType = containerTypeRepository.findById(sizeDTO.getContainerTypeId())
                .orElseThrow(() -> new RuntimeException("ContainerType not found"));
        return containerSizeRepository.findById(id)
                .map(size -> {
                    size.setLength(sizeDTO.getLength());
                    size.setWidth(sizeDTO.getWidth());
                    size.setHeight(sizeDTO.getHeight());
                    size.setVolume(sizeDTO.getVolume());
                    size.setWeight(sizeDTO.getWeight());
                    size.setLoadCapacity(sizeDTO.getLoadCapacity());
                    size.setMaxLoad(sizeDTO.getMaxLoad());
                    size.setContainerType(containerType);
                    return containerSizeRepository.save(size);
                })
                .orElseThrow(() -> new RuntimeException("ContainerSize not found"));
    }

    public void deleteSize(Integer id) {
        containerSizeRepository.deleteById(id);
    }

    public void initializeContainerTypes() {
        if (containerTypeRepository.count() == 0) {
            ContainerType type20FeetNormal = new ContainerType("20 feet","Normal");
            containerTypeRepository.save(type20FeetNormal);

            ContainerType type40FeetNormal = new ContainerType("40 feet","Normal");
            containerTypeRepository.save(type40FeetNormal);

            ContainerType type20FeetRefrigerated = new ContainerType("20 feet","Refrigerated");
            containerTypeRepository.save(type20FeetRefrigerated);

            ContainerType type40FeetRefrigerated = new ContainerType("40 feet","Refrigerated");
            containerTypeRepository.save(type40FeetRefrigerated);
        }
    }

    private ContainerSizeDTO convertToDto(ContainerSize containerSize) {
        ContainerSizeDTO dto = new ContainerSizeDTO();
        dto.setId(containerSize.getId());
        dto.setLength(containerSize.getLength());
        dto.setWidth(containerSize.getWidth());
        dto.setHeight(containerSize.getHeight());
        dto.setVolume(containerSize.getVolume());
        dto.setWeight(containerSize.getWeight());
        dto.setLoadCapacity(containerSize.getLoadCapacity());
        dto.setMaxLoad(containerSize.getMaxLoad());
        dto.setContainerTypeId(containerSize.getContainerType().getId());
        dto.setContainerTypeName(containerSize.getContainerType().getName());
        dto.setContainerTypeType(containerSize.getContainerType().getType());
        return dto;
    }
}
