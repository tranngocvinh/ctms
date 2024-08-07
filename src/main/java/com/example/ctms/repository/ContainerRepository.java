package com.example.ctms.repository;

import com.example.ctms.entity.Container;
import com.example.ctms.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
@EnableJpaRepositories
public interface ContainerRepository extends JpaRepository<Container, String> {
    Optional<Container> findByContainerCode(String containerCode);
    void deleteByContainerCode(String containerCode);
    @Query("SELECT c FROM Container c JOIN c.shipSchedules ss WHERE ss.schedule.id = :scheduleId")
    List<Container> findByScheduleId(@Param("scheduleId") Integer scheduleId);

    @Query("SELECT COALESCE(SUM(cs.weight), 0) FROM Container c JOIN c.containerSize cs JOIN c.shipSchedules ss WHERE ss.ship.id = :shipId AND c.status = 'In Transit'")
    double sumCapacityByShip(@Param("shipId") Integer shipId);
}
