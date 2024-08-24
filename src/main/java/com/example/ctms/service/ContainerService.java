package com.example.ctms.service;

import com.example.ctms.dto.ContainerDTO;
import com.example.ctms.dto.EmptyContainerDTO;
import com.example.ctms.dto.EmptyContainerDetailDTO;
import com.example.ctms.dto.EmptyContainerRequestDto;
import com.example.ctms.entity.*;
import com.example.ctms.mapper.ContainerMapper;
import com.example.ctms.mapper.EmptyContainerDTOMapper;
import com.example.ctms.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContainerService {

    private ContainerRepository containerRepository;

    private ContainerSizeRepository containerSizeRepository;

    private ShipRepository shipRepository;

    private ScheduleRepository scheduleRepository;

    private EmptyContainerRepository emptyContainerRepository;

    private PortLocationRepository portLocationRepository;

    private ContainerSupplierRepository containerSupplierRepository;

    private ShipScheduleRepository shipScheduleRepository;

    private EmptyContainerDetailRepository emptyContainerDetailRepository;

    private CustomerService customerService;

    private EmptyContainerDTOMapper emptyContainerDTOMapper;

    public ContainerService(ContainerRepository containerRepository, ContainerSizeRepository containerSizeRepository, ShipRepository shipRepository, ScheduleRepository scheduleRepository, EmptyContainerRepository emptyContainerRepository, PortLocationRepository portLocationRepository, ContainerSupplierRepository containerSupplierRepository, ShipScheduleRepository shipScheduleRepository, EmptyContainerDetailRepository emptyContainerDetailRepository, CustomerService customerService, EmptyContainerDTOMapper emptyContainerDTOMapper) {
        this.containerRepository = containerRepository;
        this.containerSizeRepository = containerSizeRepository;
        this.shipRepository = shipRepository;
        this.scheduleRepository = scheduleRepository;
        this.emptyContainerRepository = emptyContainerRepository;
        this.portLocationRepository = portLocationRepository;
        this.containerSupplierRepository = containerSupplierRepository;
        this.shipScheduleRepository = shipScheduleRepository;
        this.emptyContainerDetailRepository = emptyContainerDetailRepository;
        this.customerService = customerService;
        this.emptyContainerDTOMapper = emptyContainerDTOMapper;
    }

    public List<ContainerDTO> getAllContainers() {
        Customer customer =  customerService.getCurrentCustomer();
        List<Container> containers = null;
        if (customer.getRoles().stream().anyMatch(auth -> auth.equals("MANAGER") || auth.equals("STAFF"))) {
             containers = containerRepository.findAll();
        }else{
             containers = containerRepository.findByCustomerId(customer.getId());
        }

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



        return ContainerMapper.INSTANCE.toDTO(savedContainer);

    }

    public ContainerDTO updateContainer(String containerCode, ContainerDTO containerDTO) {
//        ContainerSize containerSize = containerSizeRepository.findById(containerDTO.containerSize().id())
//                .orElseThrow(() -> new RuntimeException("ContainerSize not found"));
//
//        PortLocation portLocation = portLocationRepository.findById(containerDTO.portLocation().getId())
//                .orElseThrow(() -> new RuntimeException("PortLocation not found"));
//
//        ContainerSupplier containerSupplier;
//        if (containerDTO.containerSupplier() != null && containerDTO.containerSupplier().id() != null) {
//            containerSupplier = containerSupplierRepository.findById(containerDTO.containerSupplier().id())
//                    .orElseThrow(() -> new RuntimeException("ContainerSupplier not found"));
//        } else {
//            containerSupplier = null;
//        }
//
//        Container updatedContainer = containerRepository.findByContainerCode(containerCode)
//                .map(container -> {
//                    container.setContainerSize(containerSize);
//                    container.setStatus(containerDTO.status());
//                    container.setPortLocation(portLocation);
//                    container.setContainerSupplier(containerSupplier);
//                    container.setHasGoods(determineHasGoods(containerDTO.status()));
//                    containerDTO.shipSchedules().forEach(shipScheduleDTO -> {
//                        Ship ship = shipRepository.findById(shipScheduleDTO.ship().id())
//                                .orElseThrow(() -> new RuntimeException("Ship not found with ID: " + shipScheduleDTO.ship().id()));
//                        Schedule schedule = scheduleRepository.findById(shipScheduleDTO.schedule().id())
//                                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + shipScheduleDTO.schedule().id()));
//
//                        List<ShipSchedule> existingShipSchedules = shipScheduleRepository.findByShipAndScheduleAndContainerIsNull(ship, schedule);
//                        if (!existingShipSchedules.isEmpty()) {
//                            ShipSchedule existingShipSchedule = existingShipSchedules.get(0);
//                            existingShipSchedule.setContainer(container);
//                            shipScheduleRepository.save(existingShipSchedule);
//                        } else {
//                            ShipSchedule newShipSchedule = new ShipSchedule(container, ship, schedule);
//                            shipScheduleRepository.save(newShipSchedule);
//                        }
//                    });
//                    return containerRepository.save(container);
//
//                })
//                .orElseThrow(() -> new RuntimeException("Container not found"));
//
        return null;
    }

    public void deleteContainer(String containerCode) {
        containerRepository.deleteByContainerCode(containerCode);
    }

    @Transactional
    public void allocateEmptyContainersToShip(EmptyContainerRequestDto request) {
        Customer customer = customerService.getCurrentCustomer();

        Ship ship = shipRepository.findById(request.getShipId())
                .orElseThrow(() -> new RuntimeException("Ship not found"));

        PortLocation portLocation = portLocationRepository.findById(request.getPortId())
                .orElseThrow(() -> new RuntimeException("Port not found"));

        double availableCapacity = ship.getCapacity() * 0.7; // 70% of ship's capacity
        double usedCapacity = containerRepository.sumCapacityByShip(request.getShipId());

        if (usedCapacity + request.getTotalCapacity() > availableCapacity) {
            throw new RuntimeException("Not enough capacity on the ship");
        }

        int isApproved = 0;
        LocalDateTime approvedDate = null ;
        if (customer.getRoles().stream().anyMatch(auth -> auth.equals("MANAGER"))) {
            isApproved = 1;
           approvedDate = LocalDateTime.now() ;
        }

        EmptyContainer emptyContainerRequest = new EmptyContainer(
                (int) request.getTotalCapacity(),
                request.getRequestTime() != null ? request.getRequestTime() : LocalDateTime.now(),
                approvedDate,
                portLocation,
                ship,
                false,
                isApproved
        );
        emptyContainerRequest.setCustomer(customer);
        emptyContainerRepository.save(emptyContainerRequest);

            List<Container> containers = null;

        for (EmptyContainerDetailDTO detail : request.getDetails()) {

                    Container eachContainer = containerRepository.findByContainerCode(detail.containerCode())
                            .orElseThrow(() -> new RuntimeException("Container not found")); ;
                // Add each container to the containers list

            // Assuming you are creating EmptyContainerDetail for some other processing
            EmptyContainerDetail emptyContainerDetail = new EmptyContainerDetail(
                    emptyContainerRequest,
                    eachContainer
            );
            eachContainer.setCustomer(customer);
            eachContainer.setEmptyContainerDetail(emptyContainerDetail);
            emptyContainerDetailRepository.save(emptyContainerDetail);
        }

        emptyContainerRequest.setFulfilled(true);
        emptyContainerRequest.setSi(false);
        emptyContainerRepository.save(emptyContainerRequest);

    }

//    private void autoGenerateContainer(EmptyContainerRequestDto request, Customer customer) {
//
//        PortLocation portLocation = portLocationRepository.findById(request.getPortId())
//                    .orElseThrow(() -> new RuntimeException("PortLocation not found"));
//
//        Container container = new Container() ;
//        container.setPortLocation(portLocation);
//        container.setStatus("In Port");
//        container.setHasGoods(true);
//        container.setIsRepair(0);
//        container.setCustomer(customer);
//        container.setLocalDateTime(LocalDateTime.now());
//        for( int i = 0 ; i < request.getDetails().size() ; i++){
//            ContainerSize containerSize = containerSizeRepository.findById(request.getDetails().get(i).getContainerSizeId())
//                    .orElseThrow(() -> new RuntimeException("ContainerSize not found"));
//            container.setContainerSize(containerSize);
//            for(int j = 0 ; j < request.getDetails().get(i).getQuantity(); j++) {
//                container.setContainerCode(generateContainerCode()) ;
//                containerRepository.save(container);
//            }
//
//        }
//
//    }


//    @Transactional
//    public void allocateEmptyContainersToPort(int numberOfContainers, String portName) {
//        EmptyContainer emptyContainerRequest = new EmptyContainer(numberOfContainers, LocalDateTime.now(), portName, null, false);
//        emptyContainerRepository.save(emptyContainerRequest);
//
//        for (int i = 0; i < numberOfContainers; i++) {
//            Container container = new Container();
//            container.setContainerCode(generateContainerCode());
//            container.setContainerSize(containerSizeRepository.findById(1).orElseThrow(() -> new RuntimeException("ContainerSize not found"))); // Assume size ID 1 for simplicity
//            container.setStatus("In Port");
//            container.setPortLocation(portLocationRepository.findByPortName(portName).orElseThrow(() -> new RuntimeException("PortLocation not found")));
//            container.setContainerSupplier(containerSupplierRepository.findById(1).orElseThrow(() -> new RuntimeException("ContainerSupplier not found"))); // Assume supplier ID 1 for simplicity
//            container.setHasGoods(false);
//            containerRepository.save(container);
//        }
//
//        emptyContainerRequest.setFulfilled(true);
//        emptyContainerRepository.save(emptyContainerRequest);
//    }

    private String generateContainerCode() {
        return UUID.randomUUID().toString().substring(0, 11).toUpperCase();
    }

    private boolean determineHasGoods(String status) {
        switch (status) {
            case "In Transit":
                return true ;
            case "In Port":
                return false;
            case "Under Maintenance":
                return false;
            default:
                return false;
        }
    }

    public List<EmptyContainerDTO> getAllEmptyContainer() {
      return  emptyContainerRepository.findByIsApprovedEquals(0).stream().map(emptyContainerDTOMapper).toList() ;
    }

    public Optional<EmptyContainerDTO> getAllEmptyContainerById(int id) {
        return emptyContainerRepository.findById(id).stream().map(emptyContainerDTOMapper).findFirst() ;
    }

    public void isApproved(int id) {
         EmptyContainer emptyContainerUpdate = emptyContainerRepository.getReferenceById(id) ;
         emptyContainerUpdate.setIsApproved(1);
         emptyContainerUpdate.setApprovalDate(LocalDateTime.now());
        //autoGenerateContainerForIsApproved(emptyContainerUpdate);
        emptyContainerRepository.save(emptyContainerUpdate) ;
    }

//    private void autoGenerateContainerForIsApproved(EmptyContainer emptyContainerUpdate) {
//        PortLocation portLocation = portLocationRepository.findById(emptyContainerUpdate.getPortLocation().getId())
//                .orElseThrow(() -> new RuntimeException("PortLocation not found"));
//
//        Container container = new Container() ;
//        container.setPortLocation(portLocation);
//        container.setStatus("In Port");
//        container.setHasGoods(true);
//        container.setCustomer(emptyContainerUpdate.getCustomer());
//        container.setLocalDateTime(LocalDateTime.now());
//        for( int i = 0 ; i < emptyContainerUpdate.getDetails().size() ; i++){
//            ContainerSize containerSize = containerSizeRepository.findById(emptyContainerUpdate.getDetails().get(i).getContainerSize().getId())
//                    .orElseThrow(() -> new RuntimeException("ContainerSize not found"));
//            container.setContainerSize(containerSize);
//            for(int j = 0 ; j < emptyContainerUpdate.getDetails().get(i).getQuantity(); j++) {
//                container.setContainerCode(generateContainerCode()) ;
//                containerRepository.save(container);
//            }
//
//        }
//    }

    public List<EmptyContainerDTO> getAllEmptyContainerIsApprove() {
        Customer customer =  customerService.getCurrentCustomer();
        if(customer.getRoles().stream().anyMatch(auth -> auth.equals("MANAGER"))) {
            return emptyContainerRepository.findByIsApprovedEquals(1).stream().map(emptyContainerDTOMapper).toList() ;
        }

        return  emptyContainerRepository.findByCustomerIdAndIsApproved(customer.getId(),1).stream().map(emptyContainerDTOMapper).toList() ;

    }

    public long getTotalContainers() {
        return containerRepository.countAllContainers();
    }

    public List<ContainerDTO> searchContainersByCode(String query) {
        List<Container> containers = containerRepository.findByContainerCodeContainingIgnoreCase(query);
        return containers.stream().map(ContainerMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    public List<ContainerDTO> findContainersByPortId(Integer portId) {
        return containerRepository.findByPortLocationId(portId).stream().map(ContainerMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
}
