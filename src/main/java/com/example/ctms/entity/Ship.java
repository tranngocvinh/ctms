package com.example.ctms.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@Entity

public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private Double capacity;

    @Column(nullable = false, unique = true)
    private String registrationNumber; // Số đăng ký của tàu

    @Column(nullable = false)
    private Integer yearBuilt; // Năm đóng tàu

    @Column(nullable = false)
    private String status; // Trạng thái của tàu (Active, Inactive)

    @OneToMany(mappedBy = "ship", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<ContainerSize> containerSizes;

    public Ship(String name, String company, Double capacity, String registrationNumber, Integer yearBuilt, String status) {
        this.name = name;
        this.company = company;
        this.capacity = capacity;
        this.registrationNumber = registrationNumber;
        this.yearBuilt = yearBuilt;
        this.status = status;
    }

    public Ship() {
    }

// Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Integer getYearBuilt() {
        return yearBuilt;
    }

    public void setYearBuilt(Integer yearBuilt) {
        this.yearBuilt = yearBuilt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ContainerSize> getContainerSizes() {
        return containerSizes;
    }

    public void setContainerSizes(List<ContainerSize> containerSizes) {
        this.containerSizes = containerSizes;
    }
}
