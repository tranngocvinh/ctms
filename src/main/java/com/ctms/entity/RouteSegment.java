package com.ctms.entity;

import jakarta.persistence.*;

@Entity
public class RouteSegment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "start_waypoint_id", nullable = false)
    private Waypoint startWaypoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end_waypoint_id", nullable = false)
    private Waypoint endWaypoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @Column(nullable = false)
    private Integer segmentOrder;

    public RouteSegment() {}

    public RouteSegment(Waypoint startWaypoint, Waypoint endWaypoint, Route route, Integer segmentOrder) {
        this.startWaypoint = startWaypoint;
        this.endWaypoint = endWaypoint;
        this.route = route;
        this.segmentOrder = segmentOrder;
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Waypoint getStartWaypoint() {
        return startWaypoint;
    }

    public void setStartWaypoint(Waypoint startWaypoint) {
        this.startWaypoint = startWaypoint;
    }

    public Waypoint getEndWaypoint() {
        return endWaypoint;
    }

    public void setEndWaypoint(Waypoint endWaypoint) {
        this.endWaypoint = endWaypoint;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Integer getSegmentOrder() {
        return segmentOrder;
    }

    public void setSegmentOrder(Integer segmentOrder) {
        this.segmentOrder = segmentOrder;
    }
}
