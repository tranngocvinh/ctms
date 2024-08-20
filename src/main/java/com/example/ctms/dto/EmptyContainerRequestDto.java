package com.example.ctms.dto;

import java.time.LocalDateTime;
import java.util.List;

public class EmptyContainerRequestDto {
    private double totalCapacity;
    private Integer shipId;
    private LocalDateTime requestTime;
    private Integer portId;
    private List<EmptyContainerDetailDTO> details;

    // Getters and Setters

    public Integer getShipId() {
        return shipId;
    }

    public void setShipId(Integer shipId) {
        this.shipId = shipId;
    }

    public double getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(double totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }

    public Integer getPortId() {
        return portId;
    }

    public void setPortId(Integer portId) {
        this.portId = portId;
    }

    public List<EmptyContainerDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<EmptyContainerDetailDTO> details) {
        this.details = details;
    }
}
