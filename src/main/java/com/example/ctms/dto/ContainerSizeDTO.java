package com.example.ctms.dto;

public class ContainerSizeDTO {
    private Integer id;
    private Double length;
    private Double width;
    private Double height;
    private Double volume;
    private Double weight;

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

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
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

    public Integer getContainerTypeId() {
        return containerTypeId;
    }

    public void setContainerTypeId(Integer containerTypeId) {
        this.containerTypeId = containerTypeId;
    }

    public String getContainerTypeName() {
        return containerTypeName;
    }

    public void setContainerTypeName(String containerTypeName) {
        this.containerTypeName = containerTypeName;
    }

    public String getContainerTypeType() {
        return containerTypeType;
    }

    public void setContainerTypeType(String containerTypeType) {
        this.containerTypeType = containerTypeType;
    }

    private Double loadCapacity;
    private Double maxLoad;
    private Integer containerTypeId;
    private String containerTypeName;
    private String containerTypeType;
}
