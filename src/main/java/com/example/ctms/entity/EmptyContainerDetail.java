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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "container_code", nullable = false)
    private Container container;

    // Constructors, Getters, and Setters

    public EmptyContainerDetail() {
    }

    public EmptyContainerDetail(EmptyContainer emptyContainer, Container container) {
        this.emptyContainer = emptyContainer;
        this.container = container;
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

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }
}

