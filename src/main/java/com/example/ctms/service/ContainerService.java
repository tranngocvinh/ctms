package com.example.ctms.service;

import com.example.ctms.dto.ContainerDTO;
import com.example.ctms.entity.*;
import com.example.ctms.mapper.ContainerMapper;
import com.example.ctms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
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
    private EmptyContainerRepository emptyContainerRepository;

    @Autowired
    private PortLocationRepository portLocationRepository;

    @Autowired
    private ContainerSupplierRepository containerSupplierRepository;

    @Autowired
    private ShipScheduleRepository shipScheduleRepository;

    public List<ContainerDTO> getAllContainers() {
        List<Container> containers = containerRepository.findAll();
        return containers.stream().map(ContainerMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    public ContainerDTO getContainerById(String containerCode) {
        Container container = containerRepository.findByContainerCode(containerCode)
                .orElseThrow(() -> new RuntimeException("Container not found"));
        return ContainerMapper.INSTANCE.toDTO(container);
    }

    @Transactional
    public ContainerDTO addContainer(ContainerDTO containerDTO) {
        ContainerSize containerSize = containerSizeRepository.findById(containerDTO.containerSize().id())
                .orElseThrow(() -> new RuntimeException("ContainerSize not found"));

        PortLocation portLocation = null;
        if (containerDTO.portLocation() != null && containerDTO.portLocation().getId() != null) {
            portLocation = portLocationRepository.findById(containerDTO.portLocation().getId())
                    .orElseThrow(() -> new RuntimeException("PortLocation not found"));
        }

        ContainerSupplier containerSupplier = null;
        if (containerDTO.containerSupplier() != null && containerDTO.containerSupplier().id() != null) {
            containerSupplier = containerSupplierRepository.findById(containerDTO.containerSupplier().id())
                    .orElseThrow(() -> new RuntimeException("ContainerSupplier not found"));
        }

        Container container = ContainerMapper.INSTANCE.toEntity(containerDTO);
        container.setContainerCode(containerDTO.containerCode());
        container.setContainerSize(containerSize);
        container.setPortLocation(portLocation);
        container.setContainerSupplier(containerSupplier);
        container.setHasGoods(determineHasGoods(containerDTO.status()));

        // Save the container first to avoid TransientPropertyValueException
        Container savedContainer = containerRepository.save(container);

        containerDTO.shipSchedules().forEach(shipScheduleDTO -> {
            Ship ship = shipRepository.findById(shipScheduleDTO.ship().id())
                    .orElseThrow(() -> new RuntimeException("Ship not found with ID: " + shipScheduleDTO.ship().id()));
            Schedule schedule = scheduleRepository.findById(shipScheduleDTO.schedule().id())
                    .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + shipScheduleDTO.schedule().id()));

            List<ShipSchedule> existingShipSchedules = shipScheduleRepository.findByShipAndScheduleAndContainerIsNull(ship, schedule);
            if (!existingShipSchedules.isEmpty()) {
                ShipSchedule existingShipSchedule = existingShipSchedules.get(0);
                existingShipSchedule.setContainer(savedContainer);
                shipScheduleRepository.save(existingShipSchedule);
            } else {
                ShipSchedule newShipSchedule = new ShipSchedule(savedContainer, ship, schedule);
                shipScheduleRepository.save(newShipSchedule);
            }
        });

        return ContainerMapper.INSTANCE.toDTO(savedContainer);
    }

    public ContainerDTO updateContainer(String containerCode, ContainerDTO containerDTO) {
        ContainerSize containerSize = containerSizeRepository.findById(containerDTO.containerSize().id())
                .orElseThrow(() -> new RuntimeException("ContainerSize not found"));

        PortLocation portLocation = portLocationRepository.findById(containerDTO.portLocation().getId())
                .orElseThrow(() -> new RuntimeException("PortLocation not found"));

        ContainerSupplier containerSupplier = containerSupplierRepository.findById(containerDTO.containerSupplier().id())
                .orElseThrow(() -> new RuntimeException("ContainerSupplier not found"));

        Container updatedContainer = containerRepository.findByContainerCode(containerCode)
                .map(container -> {
                    container.setContainerSize(containerSize);
                    container.setStatus(containerDTO.status());
                    container.setPortLocation(portLocation);
                    container.setContainerSupplier(containerSupplier);
                    container.setHasGoods(determineHasGoods(containerDTO.status()));
                    return containerRepository.save(container);
                })
                .orElseThrow(() -> new RuntimeException("Container not found"));

        return ContainerMapper.INSTANCE.toDTO(updatedContainer);
    }

    public void deleteContainer(String containerCode) {
        containerRepository.deleteByContainerCode(containerCode);
    }

    @Transactional
    public void allocateEmptyContainersToShip(double totalCapacity, Integer shipId) {
        Ship ship = shipRepository.findById(shipId)
                .orElseThrow(() -> new RuntimeException("Ship not found"));

        double availableCapacity = ship.getCapacity() * 0.7; // 70% of ship's capacity
        double usedCapacity = containerRepository.sumCapacityByShip(shipId);

        if (usedCapacity + totalCapacity > availableCapacity) {
            throw new RuntimeException("Not enough capacity on the ship");
        }

        EmptyContainer emptyContainerRequest = new EmptyContainer((int) totalCapacity, LocalDateTime.now(), null, ship, false);
        emptyContainerRepository.save(emptyContainerRequest);

        emptyContainerRequest.setFulfilled(true);
        emptyContainerRepository.save(emptyContainerRequest);
    }

    @Transactional
    public void allocateEmptyContainersToPort(int numberOfContainers, String portName) {
        EmptyContainer emptyContainerRequest = new EmptyContainer(numberOfContainers, LocalDateTime.now(), portName, null, false);
        emptyContainerRepository.save(emptyContainerRequest);

        for (int i = 0; i < numberOfContainers; i++) {
            Container container = new Container();
            container.setContainerCode(generateContainerCode());
            container.setContainerSize(containerSizeRepository.findById(1).orElseThrow(() -> new RuntimeException("ContainerSize not found"))); // Assume size ID 1 for simplicity
            container.setStatus("In Port");
            container.setPortLocation(portLocationRepository.findByPortName(portName).orElseThrow(() -> new RuntimeException("PortLocation not found")));
            container.setContainerSupplier(containerSupplierRepository.findById(1).orElseThrow(() -> new RuntimeException("ContainerSupplier not found"))); // Assume supplier ID 1 for simplicity
            container.setHasGoods(false);
            containerRepository.save(container);
        }

        emptyContainerRequest.setFulfilled(true);
        emptyContainerRepository.save(emptyContainerRequest);
    }

    private String generateContainerCode() {
        return UUID.randomUUID().toString().substring(0, 11).toUpperCase();
    }

    private boolean determineHasGoods(String status) {
        switch (status) {
            case "In Transit":
            case "In Port":
                return true;
            case "Under Maintenance":
                return false;
            default:
                return false;
        }
    }
}
