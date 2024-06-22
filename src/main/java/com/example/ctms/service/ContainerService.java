package com.example.ctms.service;

import com.example.ctms.dto.ContainerDTO;
import com.example.ctms.dto.ContainerHistoryDTO;
import com.example.ctms.entity.*;
import com.example.ctms.repository.ContainerHistoryRepository;
import com.example.ctms.repository.ContainerRepository;
import com.example.ctms.repository.ContainerSizeRepository;
import com.example.ctms.repository.ScheduleRepository;
import com.example.ctms.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContainerService {
    @Autowired
    private ContainerRepository containerRepository;

    @Autowired
    private ContainerSizeRepository containerSizeRepository;

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ContainerHistoryRepository containerHistoryRepository;

    public List<ContainerDTO> getAllContainers() {
        List<Container> containers = containerRepository.findAll();
        return containers.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public ContainerDTO getContainerById(Integer id) {
        Container container = containerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Container not found"));
        return convertToDto(container);
    }

    public ContainerDTO addContainer(ContainerDTO containerDTO) {
        ContainerSize containerSize = containerSizeRepository.findById(containerDTO.containerSizeId())
                .orElseThrow(() -> new RuntimeException("ContainerSize not found"));

        Ship ship = null;
        if (containerDTO.shipId() != null) {
            ship = shipRepository.findById(containerDTO.shipId())
                    .orElseThrow(() -> new RuntimeException("Ship not found"));
        }

        Schedule schedule = null;
        if (containerDTO.scheduleId() != null) {
            schedule = scheduleRepository.findById(containerDTO.scheduleId())
                    .orElseThrow(() -> new RuntimeException("Schedule not found"));
        }

        Container container = new Container();
        container.setContainerSize(containerSize);
        container.setStatus(containerDTO.status());
        container.setShip(ship);
        container.setSchedule(schedule);
        container.setLocation(containerDTO.location());

        Container savedContainer = containerRepository.save(container);
        return convertToDto(savedContainer);
    }

    public ContainerDTO updateContainer(Integer id, ContainerDTO containerDTO) {
        ContainerSize containerSize = containerSizeRepository.findById(containerDTO.containerSizeId())
                .orElseThrow(() -> new RuntimeException("ContainerSize not found"));

        Ship ship;
        if (containerDTO.shipId() != null) {
            ship = shipRepository.findById(containerDTO.shipId())
                    .orElseThrow(() -> new RuntimeException("Ship not found"));
        } else {
            ship = null;
        }

        Schedule schedule;
        if (containerDTO.scheduleId() != null) {
            schedule = scheduleRepository.findById(containerDTO.scheduleId())
                    .orElseThrow(() -> new RuntimeException("Schedule not found"));
        } else {
            schedule = null;
        }

        Container updatedContainer = containerRepository.findById(id)
                .map(container -> {
                    container.setContainerSize(containerSize);
                    container.setStatus(containerDTO.status());
                    container.setShip(ship);
                    container.setSchedule(schedule);
                    container.setLocation(containerDTO.location());
                    return containerRepository.save(container);
                })
                .orElseThrow(() -> new RuntimeException("Container not found"));

        return convertToDto(updatedContainer);
    }

    public void deleteContainer(Integer id) {
        containerRepository.deleteById(id);
    }

    private ContainerDTO convertToDto(Container container) {
        ContainerSize containerSize = container.getContainerSize();

        return new ContainerDTO(
                container.getId(),
                containerSize.getId(),
                containerSize.toString(),
                containerSize.getLength(),
                containerSize.getWidth(),
                containerSize.getHeight(),
                containerSize.getVolume(),
                containerSize.getWeight(),
                containerSize.getLoadCapacity(),
                containerSize.getMaxLoad(),
                container.getStatus(),
                container.getShip() != null ? container.getShip().getId() : null,
                container.getShip() != null ? container.getShip().getName() : null,
                container.getSchedule() != null ? container.getSchedule().getId() : null,
                container.getSchedule() != null ? container.getSchedule().getRoute().getName() : null,
                container.getSchedule() != null ? container.getSchedule().getDepartureTime() : null,
                container.getSchedule() != null ? container.getSchedule().getEstimatedArrivalTime() : null,
                container.getLocation(),
                container.getHistory().stream().map(this::convertHistoryToDto).collect(Collectors.toList())
        );
    }

    private ContainerHistoryDTO convertHistoryToDto(ContainerHistory history) {
        return new ContainerHistoryDTO(
                history.getId(),
                history.getTimestamp(),
                history.getStatus(),
                history.getLocation(),
                history.getShip() != null ? history.getShip().getId() : null,
                history.getShip() != null ? history.getShip().getName() : null,
                history.getSchedule() != null ? history.getSchedule().getId() : null,
                history.getSchedule() != null ? history.getSchedule().getRoute().getName() : null
        );
    }
}
