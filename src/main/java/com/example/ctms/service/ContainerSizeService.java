package com.example.ctms.service;

import com.example.ctms.dto.ContainerSizeDTO;
import com.example.ctms.entity.ContainerSize;
import com.example.ctms.entity.ContainerType;
import com.example.ctms.entity.Schedule;
import com.example.ctms.entity.Ship;
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
        return containerSizes.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<ContainerSizeDTO> getSizesByType(Integer typeId) {
        List<ContainerSize> containerSizes = containerSizeRepository.findByContainerTypeId(typeId);
        return containerSizes.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public ContainerSizeDTO addSize(ContainerSizeDTO sizeDTO) {
        ContainerType containerType = containerTypeRepository.findById(sizeDTO.containerTypeId())
                .orElseThrow(() -> new RuntimeException("ContainerType not found"));

        Ship ship = null;
        if (sizeDTO.shipId() != null) {
            ship = shipRepository.findById(sizeDTO.shipId())
                    .orElseThrow(() -> new RuntimeException("Ship not found"));
        }

        Schedule schedule = null;
        if (sizeDTO.scheduleId() != null) {
            schedule = scheduleRepository.findById(sizeDTO.scheduleId())
                    .orElseThrow(() -> new RuntimeException("Schedule not found"));
        }

        ContainerSize size = new ContainerSize();
        size.setLength(sizeDTO.length());
        size.setWidth(sizeDTO.width());
        size.setHeight(sizeDTO.height());
        size.setVolume(sizeDTO.volume());
        size.setWeight(sizeDTO.weight());
        size.setLoadCapacity(sizeDTO.loadCapacity());
        size.setMaxLoad(sizeDTO.maxLoad());
        size.setContainerType(containerType);
        size.setShip(ship);
        size.setSchedule(schedule);

        ContainerSize savedSize = containerSizeRepository.save(size);
        return convertToDto(savedSize);
    }

    public ContainerSizeDTO updateSize(Integer id, ContainerSizeDTO sizeDTO) {
        ContainerType containerType = containerTypeRepository.findById(sizeDTO.containerTypeId())
                .orElseThrow(() -> new RuntimeException("ContainerType not found"));

        Ship ship;
        if (sizeDTO.shipId() != null) {
            ship = shipRepository.findById(sizeDTO.shipId())
                    .orElseThrow(() -> new RuntimeException("Ship not found"));
        } else {
            ship = null;
        }

        Schedule schedule;
        if (sizeDTO.scheduleId() != null) {
            schedule = scheduleRepository.findById(sizeDTO.scheduleId())
                    .orElseThrow(() -> new RuntimeException("Schedule not found"));
        } else {
            schedule = null;
        }

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
                    size.setShip(ship);
                    size.setSchedule(schedule);
                    return containerSizeRepository.save(size);
                })
                .orElseThrow(() -> new RuntimeException("ContainerSize not found"));
        return convertToDto(updatedSize);
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

    private ContainerSizeDTO convertToDto(ContainerSize containerSize) {
        return new ContainerSizeDTO(
                containerSize.getId(),
                containerSize.getLength(),
                containerSize.getWidth(),
                containerSize.getHeight(),
                containerSize.getVolume(),
                containerSize.getWeight(),
                containerSize.getLoadCapacity(),
                containerSize.getMaxLoad(),
                containerSize.getContainerType().getId(),
                containerSize.getContainerType().getName(),
                containerSize.getContainerType().getType(),
                containerSize.getShip() != null ? containerSize.getShip().getId() : null,
                containerSize.getShip() != null ? containerSize.getShip().getName() : null,
                containerSize.getSchedule() != null ? containerSize.getSchedule().getId() : null,
                containerSize.getSchedule() != null ? containerSize.getSchedule().getStatus() : null,
                containerSize.getSchedule() != null ? containerSize.getSchedule().getDepartureTime() : null
        );
    }
}
