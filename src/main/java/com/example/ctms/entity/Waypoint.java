package com.example.ctms.entity;

import jakarta.persistence.*;

@Entity
public class Waypoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String portName;

    @Column(nullable = false)
    private Double lat;

    @Column(nullable = false)
    private Double lon;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    public Waypoint() {
    }

    public Waypoint(String portName, Double lat, Double lon, Route route) {
        this.portName = portName;
        this.lat = lat;
        this.lon = lon;
        this.route = route;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
