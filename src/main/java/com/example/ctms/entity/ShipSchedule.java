package com.example.ctms.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ship_schedule", uniqueConstraints = @UniqueConstraint(columnNames = {"container_code", "schedule_id"}))
public class ShipSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "container_code", nullable = true)
    private Container container;

    @ManyToOne
    @JoinColumn(name = "shipId", nullable = false)
    private Ship ship;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    public ShipSchedule() {}

    public ShipSchedule(Container container, Ship ship, Schedule schedule) {
        this.container = container;
        this.ship = ship;
        this.schedule = schedule;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
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
}
