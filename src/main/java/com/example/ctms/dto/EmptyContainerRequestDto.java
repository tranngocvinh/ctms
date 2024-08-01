package com.example.ctms.dto;

import java.time.LocalDateTime;
import java.util.List;

public class EmptyContainerRequestDto {
    private double totalCapacity;
    private Integer shipId;
    private LocalDateTime requestTime;
    private Integer portId;
    private List<ContainerDetailDto> details;

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

    public List<ContainerDetailDto> getDetails() {
        return details;
    }

    public void setDetails(List<ContainerDetailDto> details) {
        this.details = details;
    }

    public static class ContainerDetailDto {
        private Integer containerSizeId;
        private int quantity;

        // Getters and Setters

        public Integer getContainerSizeId() {
            return containerSizeId;
        }

        public void setContainerSizeId(Integer containerSizeId) {
            this.containerSizeId = containerSizeId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
