package com.example.ctms.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "schedules"})
    private Route route;

    @Column(nullable = false)
    private LocalDateTime departureTime;

    @Column
    private LocalDateTime estimatedArrivalTime;

    @Column
    private LocalDateTime actualDepartureTime;

    @Column
    private LocalDateTime actualArrivalTime;

    @Column(nullable = false)
    private String status;

    @Column(length = 1000)
    private String notes;

    @OneToMany(mappedBy = "schedule", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<ShipSchedule> shipSchedules = new ArrayList<>() ;

    @OneToMany(mappedBy = "schedule", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<ScheduleSegment> scheduleSegments = new ArrayList<>();

    public Schedule() {}

    public Schedule(Route route, LocalDateTime departureTime, LocalDateTime estimatedArrivalTime, LocalDateTime actualDepartureTime, LocalDateTime actualArrivalTime, String status, String notes) {
        this.route = route;
        this.departureTime = departureTime;
        this.estimatedArrivalTime = estimatedArrivalTime;
        this.actualDepartureTime = actualDepartureTime;
        this.actualArrivalTime = actualArrivalTime;
        this.status = status;
        this.notes = notes;
    }

    // Getters and Setters


    public List<ScheduleSegment> getScheduleSegments() {
        return scheduleSegments;
    }

    public void setScheduleSegments(List<ScheduleSegment> scheduleSegments) {
        this.scheduleSegments = scheduleSegments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getEstimatedArrivalTime() {
        return estimatedArrivalTime;
    }

    public void setEstimatedArrivalTime(LocalDateTime estimatedArrivalTime) {
        this.estimatedArrivalTime = estimatedArrivalTime;
    }

    public LocalDateTime getActualArrivalTime() {
        return actualArrivalTime;
    }

    public void setActualArrivalTime(LocalDateTime actualArrivalTime) {
        this.actualArrivalTime = actualArrivalTime;
    }

    public LocalDateTime getActualDepartureTime() {
        return actualDepartureTime;
    }

    public void setActualDepartureTime(LocalDateTime actualDepartureTime) {
        this.actualDepartureTime = actualDepartureTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<ShipSchedule> getShipSchedules() {
        return shipSchedules;
    }

    public void setShipSchedules(List<ShipSchedule> shipSchedules) {
        this.shipSchedules = shipSchedules;
    }

    public void addShipSchedule(ShipSchedule shipSchedule) {
        if (!shipSchedules.contains(shipSchedule)) {
            shipSchedules.add(shipSchedule);
        }
    }
}
