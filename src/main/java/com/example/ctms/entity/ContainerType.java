package com.example.ctms.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class ContainerType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @OneToMany(mappedBy = "containerType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContainerSize> containerSizes;

    // Constructors, getters, and setters
    public ContainerType() {
    }

    public ContainerType(String name, String type) {
        this.name = name;
        this.type = type;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ContainerSize> getContainerSizes() {
        return containerSizes;
    }

    public void setContainerSizes(List<ContainerSize> containerSizes) {
        this.containerSizes = containerSizes;
    }
}
