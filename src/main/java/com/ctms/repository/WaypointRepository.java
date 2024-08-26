package com.ctms.repository;

import com.ctms.entity.Route;
import com.ctms.entity.Waypoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaypointRepository extends JpaRepository<Waypoint, Integer> {
    List<Waypoint> findByRoute(Route route);
}
