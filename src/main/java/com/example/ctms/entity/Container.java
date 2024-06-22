package com.example.ctms.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Container {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "containerSizeId", nullable = false)
    private ContainerSize containerSize;

    @Column(nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipId")
    private Ship ship;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduleId")
    private Schedule schedule;

    @Column
    private String location;

    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContainerHistory> history = new ArrayList<>();

    public Container() {}

    public Container(ContainerSize containerSize, String status, Ship ship, Schedule schedule, String location) {
        this.containerSize = containerSize;
        this.status = status;
        this.ship = ship;
        this.schedule = schedule;
        this.location = location;
        this.history = new ArrayList<>(); // Initialize the history list
    }

// Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ContainerSize getContainerSize() {
        return containerSize;
    }

    public void setContainerSize(ContainerSize containerSize) {
        this.containerSize = containerSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<ContainerHistory> getHistory() {
        return history;
    }

    public void setHistory(List<ContainerHistory> history) {
        this.history = history;
    }
}
