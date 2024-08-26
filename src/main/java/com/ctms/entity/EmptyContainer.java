package com.ctms.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class EmptyContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private int numberOfContainers;

    @Column(nullable = false)
    private LocalDateTime requestTime;

    @Column
    private LocalDateTime approvalDate;  // Thêm trường này

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "port_id")
    private PortLocation portLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ship_id")
    private Ship ship;

    @Column(nullable = false)
    private boolean fulfilled;

    @Column(nullable = false)
    private int isApproved;

    @Column(nullable = true)
    private boolean si;

    @OneToMany(mappedBy = "emptyContainer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmptyContainerDetail> details;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;



    public EmptyContainer() {}

    public EmptyContainer(int numberOfContainers, LocalDateTime requestTime, LocalDateTime approvalDate, PortLocation portLocation, Ship ship, boolean fulfilled, int isApproved) {
        this.numberOfContainers = numberOfContainers;
        this.requestTime = requestTime;
        this.approvalDate = approvalDate;
        this.portLocation = portLocation;
        this.ship = ship;
        this.fulfilled = fulfilled;
        this.isApproved = isApproved;
    }

    // Getters and Setters


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumberOfContainers() {
        return numberOfContainers;
    }

    public void setNumberOfContainers(int numberOfContainers) {
        this.numberOfContainers = numberOfContainers;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }

    public LocalDateTime getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDateTime approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public PortLocation getPortLocation() {
        return portLocation;
    }

    public void setPortLocation(PortLocation portLocation) {
        this.portLocation = portLocation;
    }

    public boolean isFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    public int getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(int isApproved) {
        this.isApproved = isApproved;
    }

    public List<EmptyContainerDetail> getDetails() {
        return details;
    }

    public void setDetails(List<EmptyContainerDetail> details) {
        this.details = details;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isSi() {
        return si;
    }

    public void setSi(boolean si) {
        this.si = si;
    }
}
