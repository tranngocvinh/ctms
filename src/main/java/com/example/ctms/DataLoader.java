package com.example.ctms;

import com.example.ctms.entity.*;
import com.example.ctms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
@Profile("data-load")
//@EnableJpaRepositories
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ContainerTypeRepository containerTypeRepository;

    @Autowired
    private ContainerSizeRepository containerSizeRepository;

    @Autowired
    private ContainerRepository containerRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private WaypointRepository waypointRepository;

    @Autowired
    private ShipScheduleRepository shipScheduleRepository;

    @Autowired
    private PortLocationRepository portLocationRepository;

    @Autowired
    private ContainerSupplierRepository containerSupplierRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Initialize Container Types
        ContainerType type20FeetNormal = new ContainerType("20 feet", "Normal");
        ContainerType type40FeetNormal = new ContainerType("40 feet", "Normal");
        ContainerType type20FeetRefrigerated = new ContainerType("20 feet", "Refrigerated");
        ContainerType type40FeetRefrigerated = new ContainerType("40 feet", "Refrigerated");

        // Save Container Types to DB
        containerTypeRepository.saveAll(Arrays.asList(type20FeetNormal, type40FeetNormal, type20FeetRefrigerated, type40FeetRefrigerated));

        // Fetch saved ContainerTypes from DB to ensure they are not transient
        type20FeetNormal = containerTypeRepository.findByNameAndType("20 feet", "Normal").get(0);
        type40FeetNormal = containerTypeRepository.findByNameAndType("40 feet", "Normal").get(0);
        type20FeetRefrigerated = containerTypeRepository.findByNameAndType("20 feet", "Refrigerated").get(0);
        type40FeetRefrigerated = containerTypeRepository.findByNameAndType("40 feet", "Refrigerated").get(0);

        // Initialize Container Sizes with persisted ContainerTypes
        ContainerSize size20FeetNormal1 = new ContainerSize(6.0, 2.4, 2.6, 37.44, 2000.1, 28000.0, 30000.0, type20FeetNormal);
        ContainerSize size40FeetNormal1 = new ContainerSize(12.0, 2.4, 2.6, 74.88, 4000.1, 56000.0, 60000.0, type40FeetNormal);

        containerSizeRepository.saveAll(Arrays.asList(size20FeetNormal1, size40FeetNormal1));

        // Initialize Ships
        Ship ship1 = new Ship("Vina Express", "Vina Shipping", 50000.0, "VN12345", 2010, "ACTIVE");
        Ship ship2 = new Ship("Pacific Trader", "Pacific Shipping", 100000.0, "PA54321", 2015, "ACTIVE");

        shipRepository.saveAll(Arrays.asList(ship1, ship2));

        // Initialize Routes
        Route route1 = new Route("Route A", 1440, 1500.0, "ACTIVE", "Route from Haiphong to Singapore");
        Route route2 = new Route("Route B", 2880, 3000.0, "ACTIVE", "Route from Singapore to Shanghai");

        routeRepository.saveAll(Arrays.asList(route1, route2));

        // Initialize Waypoints for Route1
        Waypoint waypoint1Route1 = new Waypoint("Haiphong Port", 20.84274, 106.7726, route1);
        Waypoint waypoint2Route1 = new Waypoint("Singapore Port", 1.3521, 103.8198, route1);
        Waypoint waypoint3Route1 = new Waypoint("Australia Port", 2.3521, 103.8198, route1);

        waypointRepository.saveAll(Arrays.asList(waypoint1Route1, waypoint2Route1,waypoint3Route1));

        // Initialize Waypoints for Route2
        Waypoint waypoint1Route2 = new Waypoint("Singapore Port", 1.3521, 103.8198, route2);
        Waypoint waypoint2Route2 = new Waypoint("Shanghai Port", 31.2304, 121.4737, route2);

        waypointRepository.saveAll(Arrays.asList(waypoint1Route2, waypoint2Route2));

        // Initialize Schedules
        Schedule schedule1 = new Schedule("QQ111",route1, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
        Schedule schedule2 = new Schedule("AA121",route2, LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(5));

        scheduleRepository.saveAll(Arrays.asList(schedule1, schedule2));



        // Initialize Port Locations
        PortLocation portLocation1 = new PortLocation("Haiphong Port", 20.84274, 106.7726);
        PortLocation portLocation2 = new PortLocation("Singapore Port", 1.3521, 103.8198);
        PortLocation portLocation3 = new PortLocation("Australia Port", 1.3521, 103.8198);

        portLocationRepository.saveAll(Arrays.asList(portLocation1, portLocation2,portLocation3));

        // Initialize Container Suppliers
        ContainerSupplier containerSupplier1 = new ContainerSupplier("Supplier A", "Address A", "123456789", "emailA@example.com", "www.supplierA.com", "Repair", null);
        ContainerSupplier containerSupplier2 = new ContainerSupplier("Supplier B", "Address B", "987654321", "emailB@example.com", "www.supplierB.com", "Repair", null);
        containerSupplierRepository.saveAll(Arrays.asList(containerSupplier1, containerSupplier2));

        // Initialize Containers
        Container container1 = new Container("MSKU1234567", size20FeetNormal1, "Under Maintenance", null, null, false);
        Container container2 = new Container("TEMU7654321", size20FeetNormal1, "Under Maintenance", null, null, false);
        Container container3 = new Container("CSQU9876543", size40FeetNormal1, "Under Maintenance", null, null, false);

        containerRepository.saveAll(Arrays.asList(container1, container2, container3));

        // Initialize ShipSchedule (intermediate table) entries
        ShipSchedule shipSchedule1 = new ShipSchedule(container1,ship1, schedule1);
        ShipSchedule shipSchedule2 = new ShipSchedule(container2,ship2, schedule2);
        shipScheduleRepository.saveAll(Arrays.asList(shipSchedule1, shipSchedule2));
// Associate ship schedules with containers
//        container2.setShipSchedules(Arrays.asList(shipSchedule1));
//        container3.setShipSchedules(Arrays.asList(shipSchedule2));


        containerRepository.saveAll(Arrays.asList(container2, container3));
    }
}
