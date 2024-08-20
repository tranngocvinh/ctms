package com.example.ctms.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
public class ShipSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ship_id", nullable = false)
    private Ship ship;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    // If a ShipSchedule can have multiple containers, use a List to represent the one-to-many relationship
    @OneToMany(mappedBy = "shipSchedule", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Container> containers;

    public ShipSchedule() {}

    public ShipSchedule(List<Container> containers,Ship ship, Schedule schedule) {
        this.ship = ship;
        this.containers = containers;
        this.schedule = schedule;
    }
// Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public List<Container> getContainers() {
        return containers;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }
}
