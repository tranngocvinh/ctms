package com.ctms.test;

import com.ctms.entity.*;
import com.ctms.repository.*;
import com.ctms.dto.ContainerDTO;
import com.ctms.dto.EmptyContainerRequestDto;
import com.ctms.mapper.ContainerMapper;
import com.ctms.mapper.EmptyContainerDTOMapper;
import com.ctms.service.ContainerService;
import com.ctms.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ContainerServiceDatabaseTest {

    @Autowired
    private ContainerRepository containerRepository;

    @Autowired
    private ContainerTypeRepository containerTypeRepository;

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

    @Autowired
    private EmptyContainerDetailRepository emptyContainerDetailRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private EmptyContainerDTOMapper emptyContainerDTOMapper;

    private ContainerService containerService;

    @BeforeEach
    void setUp() {
        ContainerType type20FeetNormal = new ContainerType("20 feet", "Normal");
        ContainerType type40FeetNormal = new ContainerType("40 feet", "Normal");
        ContainerType type20FeetRefrigerated = new ContainerType("20 feet", "Refrigerated");
        ContainerType type40FeetRefrigerated = new ContainerType("40 feet", "Refrigerated");

        containerTypeRepository.saveAll(Arrays.asList(type20FeetNormal, type40FeetNormal, type20FeetRefrigerated, type40FeetRefrigerated));

        containerService = new ContainerService(containerRepository, containerSizeRepository, shipRepository, scheduleRepository, emptyContainerRepository, portLocationRepository, containerSupplierRepository, shipScheduleRepository, emptyContainerDetailRepository, customerService, emptyContainerDTOMapper);
    }

    @Test
    void testGetAllContainersForManager() {
        // Arrange
        Customer customer = new Customer();
        customer.setRoles(Arrays.asList("MANAGER"));
        when(customerService.getCurrentCustomer()).thenReturn(customer);

        ContainerType type20FeetNormal = (ContainerType) containerTypeRepository.findByTypeAndSize("20 feet", "Normal").orElseThrow();
        Container container1 = new Container("CNTR_001", type20FeetNormal, "In Transit");
        Container container2 = new Container("CNTR_002", type20FeetNormal, "In Port");

        containerRepository.saveAll(Arrays.asList(container1, container2));

        // Act
        List<ContainerDTO> result = containerService.getAllContainers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(dto -> dto.containerCode().equals("CNTR_001")));
        assertTrue(result.stream().anyMatch(dto -> dto.containerCode().equals("CNTR_002")));
    }

    @Test
    void testGetAllContainersForNonManager() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(1);
        customer.setRoles(Arrays.asList("USER"));
        when(customerService.getCurrentCustomer()).thenReturn(customer);

        ContainerType type20FeetNormal = containerTypeRepository.findByTypeAndSize("20 feet", "Normal").orElseThrow();
        Container container1 = new Container("CNTR_003", type20FeetNormal, "In Transit");

        containerRepository.save(container1);

        EmptyContainer emptyContainer = new EmptyContainer(1, LocalDateTime.now(), null, null, null, false, 1);
        emptyContainer.setCustomer(customer);
        emptyContainerRepository.save(emptyContainer);

        EmptyContainerDetail emptyContainerDetail = new EmptyContainerDetail(emptyContainer, container1);
        emptyContainerDetailRepository.save(emptyContainerDetail);

        // Act
        List<ContainerDTO> result = containerService.getAllContainers();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.stream().anyMatch(dto -> dto.containerCode().equals("CNTR_003")));
    }

    @Test
    void testGetContainerById() {
        // Arrange
        String containerCode = "CNTR_001";
        ContainerType type20FeetNormal = containerTypeRepository.findByTypeAndSize("20 feet", "Normal").orElseThrow();
        Container container = new Container(containerCode, type20FeetNormal, "In Transit");
        containerRepository.save(container);

        // Act
        ContainerDTO result = containerService.getContainerById(containerCode);

        // Assert
        assertNotNull(result);
        assertEquals(containerCode, result.containerCode());
    }

    @Test
    void testGetContainerByIdNotFound() {
        // Arrange
        String containerCode = "NOT_EXISTING_CODE";

        // Act & Assert
        assertThrows(RuntimeException.class, () -> containerService.getContainerById(containerCode));
    }

    @Test
    @Transactional
    void testAddContainer() {
        // Arrange
        ContainerType type20FeetNormal = (ContainerType) containerTypeRepository.findByTypeAndSize("20 feet", "Normal").orElseThrow();
        ContainerSize containerSize = new ContainerSize(1, "Small");
        containerSizeRepository.save(containerSize);

        PortLocation portLocation = new PortLocation(1, "Port A");
        portLocationRepository.save(portLocation);

        ContainerSupplier containerSupplier = new ContainerSupplier(1, "Supplier A");
        containerSupplierRepository.save(containerSupplier);

        ContainerDTO containerDTO = new ContainerDTO(
                "CNTR_004",
                containerSize,
                portLocation,
                containerSupplier,
                "In Transit"
        );

        // Act
        ContainerDTO result = containerService.addContainer(containerDTO);

        // Assert
        assertNotNull(result);
        assertEquals("CNTR_004", result.containerCode());
    }

    @Test
    @Transactional
    void testAllocateEmptyContainersToShip() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(1);
        customer.setRoles(Arrays.asList("MANAGER"));
        when(customerService.getCurrentCustomer()).thenReturn(customer);

        Ship ship = new Ship(1, "Ship A", 1000);
        shipRepository.save(ship);

        PortLocation portLocation = new PortLocation(1, "Port A");
        portLocationRepository.save(portLocation);

        EmptyContainerRequestDto request = new EmptyContainerRequestDto(
                1,
                500.0,
                portLocation.getId(),
                ship.getId(),
                LocalDateTime.now(),
                Arrays.asList(new EmptyContainerDetailDTO("CNTR_005"))
        );

        ContainerType type20FeetNormal = (ContainerType) containerTypeRepository.findByTypeAndSize("20 feet", "Normal").orElseThrow();
        Container container = new Container("CNTR_005", type20FeetNormal, "In Port");
        containerRepository.save(container);

        // Act
        containerService.allocateEmptyContainersToShip(request);

        // Assert
        List<EmptyContainer> emptyContainers = emptyContainerRepository.findAll();
        assertEquals(1, emptyContainers.size());
        assertTrue(emptyContainers.get(0).isFulfilled());
    }
}