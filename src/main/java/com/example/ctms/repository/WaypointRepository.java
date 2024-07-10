package com.example.ctms.repository;

import com.example.ctms.entity.Route;
import com.example.ctms.entity.Waypoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface WaypointRepository extends JpaRepository<Waypoint, Integer> {
    List<Waypoint> findByRoute(Route route);
}
