package com.ctms.dto;

public class SIDTO {

    private Integer id ;
    private Integer emptyContainerId;
    private Integer cargoTypeId;
    private Double cargoWeight;
    private Double cargoVolume;

    public SIDTO(Integer id, Integer emptyContainerId, Integer cargoTypeId, Double cargoWeight, Double cargoVolume) {
        this.id = id ;
        this.emptyContainerId = emptyContainerId;
        this.cargoTypeId = cargoTypeId;
        this.cargoWeight = cargoWeight;
        this.cargoVolume = cargoVolume;
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmptyContainerId() {
        return emptyContainerId;
    }

    public void setEmptyContainerId(Integer emptyContainerId) {
        this.emptyContainerId = emptyContainerId;
    }

    public Integer getCargoTypeId() {
        return cargoTypeId;
    }

    public void setCargoTypeId(Integer cargoTypeId) {
        this.cargoTypeId = cargoTypeId;
    }

    public Double getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(Double cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public Double getCargoVolume() {
        return cargoVolume;
    }

    public void setCargoVolume(Double cargoVolume) {
        this.cargoVolume = cargoVolume;
    }
}
