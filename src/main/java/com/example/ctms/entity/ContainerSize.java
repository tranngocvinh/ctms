package com.example.ctms.entity;

import jakarta.persistence.*;

@Entity
public class ContainerSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double length;
    private Double width;
    private Double height;
    private Double volume;
    private Double weight;
    private Double loadCapacity;
    private Double maxLoad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "containerTypeId", nullable = false)
    private ContainerType containerType;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(Double loadCapacity) {
        this.loadCapacity = loadCapacity;
    }

    public Double getMaxLoad() {
        return maxLoad;
    }

    public void setMaxLoad(Double maxLoad) {
        this.maxLoad = maxLoad;
    }

    public ContainerType getContainerType() {
        return containerType;
    }

    public void setContainerType(ContainerType containerType) {
        this.containerType = containerType;
    }
}