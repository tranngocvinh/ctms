package com.ctms.repository;

import com.ctms.entity.RouteSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteSegmentRepository extends JpaRepository<RouteSegment, Integer> {
    List<RouteSegment> findByRouteId(Integer routeId);
}
