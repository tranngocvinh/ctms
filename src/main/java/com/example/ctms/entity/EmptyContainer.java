package com.example.ctms.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class EmptyContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private int numberOfContainers;

    @Column(nullable = false)
    private LocalDateTime requestTime;

    @Column
    private String portName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ship_id")
    private Ship ship;

    @Column(nullable = false)
    private boolean fulfilled;

    @OneToMany(mappedBy = "emptyContainer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmptyContainerDetail> details;

    // Constructors, Getters, and Setters

    public EmptyContainer() {}

    public EmptyContainer(int numberOfContainers, LocalDateTime requestTime, String portName, Ship ship, boolean fulfilled) {
        this.numberOfContainers = numberOfContainers;
        this.requestTime = requestTime;
        this.portName = portName;
        this.ship = ship;
        this.fulfilled = fulfilled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumberOfContainers() {
        return numberOfContainers;
    }

    public void setNumberOfContainers(int numberOfContainers) {
        this.numberOfContainers = numberOfContainers;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public boolean isFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    public List<EmptyContainerDetail> getDetails() {
        return details;
    }

    public void setDetails(List<EmptyContainerDetail> details) {
        this.details = details;
    }
}
