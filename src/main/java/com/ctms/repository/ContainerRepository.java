package com.ctms.repository;

import com.ctms.entity.Container;
import com.ctms.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
@EnableJpaRepositories
public interface ContainerRepository extends JpaRepository<Container, String> {
    Optional<Container> findByContainerCode(String containerCode);
    void deleteByContainerCode(String containerCode);
    @Query("SELECT c FROM Container c JOIN c.shipSchedule ss WHERE ss.schedule.id = :scheduleId")
    List<Container> findByScheduleId(@Param("scheduleId") Integer scheduleId);

    @Query("SELECT COALESCE(SUM(cs.loadCapacity), 0) FROM Container c JOIN c.containerSize cs JOIN c.shipSchedule ss WHERE ss.ship.id = :shipId AND c.status = 'In Transit'")
    double sumCapacityByShip(@Param("shipId") Integer shipId);

    List<Container> findByCustomerId(Integer userId);

    @Query("SELECT COUNT(c) FROM Container c")
    long countAllContainers();

    List<Container> findByContainerCodeContainingIgnoreCase(String query);

    List<Container> findByPortLocationIdAndCustomerId(Integer portLocation_id, Integer customer);


}
