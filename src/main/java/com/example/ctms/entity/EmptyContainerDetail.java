package com.example.ctms.entity;

import jakarta.persistence.*;

@Entity
public class EmptyContainerDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empty_container_id", nullable = false)
    private EmptyContainer emptyContainer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "container_size_id", nullable = false)
    private ContainerSize containerSize;

    @Column(nullable = false)
    private int quantity;

    // Constructors, Getters, and Setters

    public EmptyContainerDetail() {}

    public EmptyContainerDetail(EmptyContainer emptyContainer,ContainerSize containerSize, int quantity) {
        this.emptyContainer = emptyContainer;
        this.containerSize = containerSize;
        this.quantity = quantity;
    }

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


    public ContainerSize getContainerSize() {
        return containerSize;
    }

    public void setContainerSize(ContainerSize containerSize) {
        this.containerSize = containerSize;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
