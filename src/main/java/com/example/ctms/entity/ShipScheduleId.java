package com.example.ctms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ShipScheduleId implements Serializable {

    @Column(name = "container_code")
    private String containerCode;

    @Column(name = "schedule_id")
    private Integer scheduleId;

    public ShipScheduleId() {}

    public ShipScheduleId(String containerCode, Integer scheduleId) {
        this.containerCode = containerCode;
        this.scheduleId = scheduleId;
    }

    // Getters, setters, equals, and hashCode methods

    public String getContainerCode() {
        return containerCode;
    }

    public void setContainerCode(String containerCode) {
        this.containerCode = containerCode;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShipScheduleId that = (ShipScheduleId) o;
        return Objects.equals(containerCode, that.containerCode) && Objects.equals(scheduleId, that.scheduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(containerCode, scheduleId);
    }
}
