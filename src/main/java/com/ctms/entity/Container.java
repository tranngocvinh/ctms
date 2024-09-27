package com.ctms.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @Column(nullable = true)
    private int isApprove ;

    @Column(nullable = true)
    private LocalDateTime localDateTime ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipSchedule_id")
    private ShipSchedule shipSchedule;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emptyContainerDetail_id")
    private EmptyContainerDetail emptyContainerDetail;

    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContainerHistory> history = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "delivery_order_id")
    private DeliveryOrder deliveryOrder;

    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Repair> repairs;

    @Column(nullable = true)
    private int isRepair ;
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

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }


    public int getIsApprove() {
        return isApprove;
    }

    public void setIsApprove(int isApprove) {
        this.isApprove = isApprove;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<ContainerHistory> getHistory() {
        return history;
    }

    public void setHistory(List<ContainerHistory> history) {
        this.history = history;
    }

    public EmptyContainerDetail getEmptyContainerDetail() {
        return emptyContainerDetail;
    }

    public void setEmptyContainerDetail(EmptyContainerDetail emptyContainerDetail) {
        this.emptyContainerDetail = emptyContainerDetail;
    }

    public DeliveryOrder getDeliveryOrder() {
        return deliveryOrder;
    }

    public void setDeliveryOrder(DeliveryOrder deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }


    public ShipSchedule getShipSchedule() {
        return shipSchedule;
    }

    public void setShipSchedule(ShipSchedule shipSchedule) {
        this.shipSchedule = shipSchedule;
    }

    public List<Repair> getRepairs() {
        return repairs;
    }

    public void setRepairs(List<Repair> repairs) {
        this.repairs = repairs;
    }

    public int getIsRepair() {
        return isRepair;
    }

    public void setIsRepair(int isRepair) {
        this.isRepair = isRepair;
    }
}
