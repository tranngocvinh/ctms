package com.example.ctms.entity;

import jakarta.persistence.*;

import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "clean")
public class Cleaning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "container_code")
    private Container container;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private ContainerSupplier supplier;

    private Double CleaningCost;

    private LocalDate CleaningDate;

    @Column(length = 1000)
    private String description;

    @Column(nullable = true)
    private int isCleaning ;

    @Column(nullable = true)
    private int isPayment ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public ContainerSupplier getSupplier() {
        return supplier;
    }

    public void setSupplier(ContainerSupplier supplier) {
        this.supplier = supplier;
    }

    public Double getCleaningCost(Double cleaningCost) {
        return CleaningCost;
    }

    public void setCleaningCost(Double cleaningCost) {
        CleaningCost = cleaningCost;
    }

    public LocalDate getCleaningDate(LocalDate cleaningDate) {
        return CleaningDate;
    }

    public void setCleaningDate(LocalDate cleaningDate) {
        CleaningDate = cleaningDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsCleaning() {
        return isCleaning;
    }

    public void setIsCleaning(int isCleaning) {
        this.isCleaning = isCleaning;
    }

    public int getIsPayment() {
        return isPayment;
    }

    public void setIsPayment(int isPayment) {
        this.isPayment = isPayment;
    }
}
