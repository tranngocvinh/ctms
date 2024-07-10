package com.example.ctms.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Container {
    @Id
    @Column(length = 11, unique = true)
    private String containerCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "containerSizeId", nullable = false)
    private ContainerSize containerSize;

    @Column(nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portLocationId")
    private PortLocation portLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "containerSupplierId")
    private ContainerSupplier containerSupplier;

    @Column(nullable = false)
    private boolean hasGoods;

    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContainerHistory> history = new ArrayList<>();

    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShipSchedule> shipSchedules = new ArrayList<>();


    // Getters and Setters
    public Container() {}

    public Container(String containerCode, ContainerSize containerSize, String status, PortLocation portLocation, ContainerSupplier containerSupplier, boolean hasGoods) {
        this.containerCode = containerCode;
        this.containerSize = containerSize;
        this.status = status;
        this.portLocation = portLocation;
        this.containerSupplier = containerSupplier;
        this.hasGoods = hasGoods;
    }

    // Getters and setters omitted for brevity


    public String getContainerCode() {
        return containerCode;
    }

    public void setContainerCode(String containerCode) {
        this.containerCode = containerCode;
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

    public PortLocation getPortLocation() {
        return portLocation;
    }

    public void setPortLocation(PortLocation portLocation) {
        this.portLocation = portLocation;
    }

    public ContainerSupplier getContainerSupplier() {
        return containerSupplier;
    }

    public void setContainerSupplier(ContainerSupplier containerSupplier) {
        this.containerSupplier = containerSupplier;
    }

    public boolean isHasGoods() {
        return hasGoods;
    }

    public void setHasGoods(boolean hasGoods) {
        this.hasGoods = hasGoods;
    }

    public List<ContainerHistory> getHistory() {
        return history;
    }

    public void setHistory(List<ContainerHistory> history) {
        this.history = history;
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
