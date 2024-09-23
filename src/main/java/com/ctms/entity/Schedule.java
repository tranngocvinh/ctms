package com.ctms.entity;

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

    @Column(length = 1000)
    private String codeSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "schedules"})
    private Route route;

    @Column(nullable = false)
    private LocalDateTime departureTime;

    @Column
    private LocalDateTime estimatedArrivalTime;


    @OneToMany(mappedBy = "schedule", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<ShipSchedule> shipSchedules = new ArrayList<>() ;

    @OneToMany(mappedBy = "schedule", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<ScheduleSegment> scheduleSegments = new ArrayList<>();

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeliveryOrder> deliveryOrders = new ArrayList<>();

    public Schedule() {}

    public Schedule(String codeSchedule, Route route, LocalDateTime departureTime, LocalDateTime estimatedArrivalTime) {
        this.route = route;
        this.departureTime = departureTime;
        this.estimatedArrivalTime = estimatedArrivalTime;
        this.codeSchedule = codeSchedule;
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

    public String getCodeSchedule() {
        return codeSchedule;
    }

    public void setCodeSchedule(String codeSchedule) {
        this.codeSchedule = codeSchedule;
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

    public List<DeliveryOrder> getDeliveryOrders() {
        return deliveryOrders;
    }

    public void setDeliveryOrders(List<DeliveryOrder> deliveryOrders) {
        this.deliveryOrders = deliveryOrders;
    }
}
