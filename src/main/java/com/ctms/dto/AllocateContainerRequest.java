package com.ctms.dto;

public class AllocateContainerRequest {
    private int numberOfContainers;
    private Integer shipId; // shipId or portName will be provided, not both
    private String portName;

    // Getters and Setters
    public int getNumberOfContainers() {
        return numberOfContainers;
    }

    public void setNumberOfContainers(int numberOfContainers) {
        this.numberOfContainers = numberOfContainers;
    }

    public Integer getShipId() {
        return shipId;
    }

    public void setShipId(Integer shipId) {
        this.shipId = shipId;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }
}
