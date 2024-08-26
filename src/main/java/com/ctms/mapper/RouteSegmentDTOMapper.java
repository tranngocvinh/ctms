package com.ctms.mapper;

import com.ctms.dto.RouteSegmentDTO;
import com.ctms.entity.RouteSegment;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class RouteSegmentDTOMapper implements Function<RouteSegment, RouteSegmentDTO> {
    @Override
    public RouteSegmentDTO apply(RouteSegment routeSegment) {
        return new RouteSegmentDTO(
                routeSegment.getId(),
                routeSegment.getStartWaypoint().getId(),
                routeSegment.getEndWaypoint().getId(),
                routeSegment.getSegmentOrder()
        );
    }
}
