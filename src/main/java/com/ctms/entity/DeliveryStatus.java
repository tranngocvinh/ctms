package com.ctms.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class DeliveryStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_order_id", nullable = false)
    private DeliveryOrder deliveryOrder;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime statusDate;

    // Constructors, Getters and Setters

    public DeliveryStatus() {
    }

    public DeliveryStatus(DeliveryOrder deliveryOrder, String status, LocalDateTime statusDate) {
        this.deliveryOrder = deliveryOrder;
        this.status = status;
        this.statusDate = statusDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DeliveryOrder getDeliveryOrder() {
        return deliveryOrder;
    }

    public void setDeliveryOrder(DeliveryOrder deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(LocalDateTime statusDate) {
        this.statusDate = statusDate;
    }
}
