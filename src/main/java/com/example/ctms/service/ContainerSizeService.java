package com.example.ctms.service;

import com.example.ctms.dto.ContainerSizeDTO;
import com.example.ctms.entity.ContainerSize;
import com.example.ctms.entity.ContainerType;
import com.example.ctms.entity.Schedule;
import com.example.ctms.entity.Ship;
import com.example.ctms.mapper.ContainerMapper;
import com.example.ctms.repository.ContainerSizeRepository;
import com.example.ctms.repository.ContainerTypeRepository;
import com.example.ctms.repository.ScheduleRepository;
import com.example.ctms.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContainerSizeService {

    @Autowired
    private ContainerSizeRepository containerSizeRepository;

    @Autowired
    private ContainerTypeRepository containerTypeRepository;

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<ContainerSizeDTO> getAllSizes() {
        List<ContainerSize> containerSizes = containerSizeRepository.findAll();
        return containerSizes.stream().map(ContainerMapper.INSTANCE::toContainerSizeDTO).collect(Collectors.toList());
    }

    public List<ContainerSizeDTO> getSizesByType(Integer typeId) {
        List<ContainerSize> containerSizes = containerSizeRepository.findByContainerTypeId(typeId);
        return containerSizes.stream().map(ContainerMapper.INSTANCE::toContainerSizeDTO).collect(Collectors.toList());
    }

    public ContainerSizeDTO addSize(ContainerSizeDTO sizeDTO) {
        ContainerType containerType = containerTypeRepository.findById(sizeDTO.containerType().id())
                .orElseThrow(() -> new RuntimeException("ContainerType not found"));



        ContainerSize size = ContainerMapper.INSTANCE.toContainerSize(sizeDTO);
        size.setContainerType(containerType);


        ContainerSize savedSize = containerSizeRepository.save(size);
        return ContainerMapper.INSTANCE.toContainerSizeDTO(savedSize);
    }

    public ContainerSizeDTO updateSize(Integer id, ContainerSizeDTO sizeDTO) {
        ContainerType containerType = containerTypeRepository.findById(sizeDTO.containerType().id())
                .orElseThrow(() -> new RuntimeException("ContainerType not found"));



        ContainerSize updatedSize = containerSizeRepository.findById(id)
                .map(size -> {
                    size.setLength(sizeDTO.length());
                    size.setWidth(sizeDTO.width());
                    size.setHeight(sizeDTO.height());
                    size.setVolume(sizeDTO.volume());
                    size.setWeight(sizeDTO.weight());
                    size.setLoadCapacity(sizeDTO.loadCapacity());
                    size.setMaxLoad(sizeDTO.maxLoad());
                    size.setContainerType(containerType);

                    return containerSizeRepository.save(size);
                })
                .orElseThrow(() -> new RuntimeException("ContainerSize not found"));
        return ContainerMapper.INSTANCE.toContainerSizeDTO(updatedSize);
    }

    public void deleteSize(Integer id) {
        containerSizeRepository.deleteById(id);
    }

    public void initializeContainerTypes() {
        if (containerTypeRepository.count() == 0) {
            ContainerType type20FeetNormal = new ContainerType("20 feet", "Normal");
            containerTypeRepository.save(type20FeetNormal);

            ContainerType type40FeetNormal = new ContainerType("40 feet", "Normal");
            containerTypeRepository.save(type40FeetNormal);

            ContainerType type20FeetRefrigerated = new ContainerType("20 feet", "Refrigerated");
            containerTypeRepository.save(type20FeetRefrigerated);

            ContainerType type40FeetRefrigerated = new ContainerType("40 feet", "Refrigerated");
            containerTypeRepository.save(type40FeetRefrigerated);
        }
    }
}
