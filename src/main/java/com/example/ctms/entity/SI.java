package com.example.ctms.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empty_container_id", nullable = false)
    private EmptyContainer emptyContainer;

    @ManyToOne(fetch = FetchType.LAZY)
    private CargoType cargoType;

    @Column(nullable = false)
    private Double cargoWeight;

    @Column(nullable = false)
    private Double cargoVolume;

    public SI(EmptyContainer emptyContainer, CargoType cargoType, Double cargoWeight, Double cargoVolume) {
        this.emptyContainer = emptyContainer;
        this.cargoType = cargoType;
        this.cargoWeight = cargoWeight;
        this.cargoVolume = cargoVolume;
    }

    public SI() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EmptyContainer getEmptyContainer() {
        return emptyContainer;
    }

    public void setEmptyContainer(EmptyContainer emptyContainer) {
        this.emptyContainer = emptyContainer;
    }

    public Double getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(Double cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public CargoType getCargoType() {
        return cargoType;
    }

    public void setCargoType(CargoType cargoType) {
        this.cargoType = cargoType;
    }

    public Double getCargoVolume() {
        return cargoVolume;
    }

    public void setCargoVolume(Double cargoVolume) {
        this.cargoVolume = cargoVolume;
    }
}