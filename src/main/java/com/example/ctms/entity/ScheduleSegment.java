package com.example.ctms.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ScheduleSegment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "ship_id" , nullable = false)
    private Ship ship;

    @ManyToOne
    @JoinColumn(name = "route_segment_id", nullable = false)
    private RouteSegment routeSegment;

    @Column(nullable = false)
    private LocalDateTime departureTime;

    @Column(nullable = false)
    private LocalDateTime arrivalTime;

    public ScheduleSegment(){} ;

    public ScheduleSegment(Schedule schedule, RouteSegment routeSegment, LocalDateTime departureTime, LocalDateTime arrivalTime, Ship ship) {
        this.schedule = schedule;
        this.routeSegment = routeSegment;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.ship = ship;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public RouteSegment getRouteSegment() {
        return routeSegment;
    }

    public void setRouteSegment(RouteSegment routeSegment) {
        this.routeSegment = routeSegment;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }
}
