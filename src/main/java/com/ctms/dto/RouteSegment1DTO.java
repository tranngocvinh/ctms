package com.ctms.dto;

import com.ctms.entity.Route;
import com.ctms.entity.Waypoint;

public record RouteSegment1DTO(
        Integer id,
        Waypoint startWaypoint,
        Waypoint endWaypoint,
        Route route,
        Integer segmentOrder
) {
}
