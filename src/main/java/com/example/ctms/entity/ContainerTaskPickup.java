package com.example.ctms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
public class ContainerTaskPickup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pickupId;
    @Column(nullable = false)
    private String pickupDoNumber;
    @Column(nullable = false)
    private String pickupBookingNumber;
    @Column(nullable = false)
    private String pickupCtnNumber;
    @Column(nullable = false)
    private LocalDate pickupExpDate;
    @Column(nullable = false)
    private LocalDate pickupExpCtnDate;
    @Column(nullable = false)
    private String shipperName;
    @Column(nullable = false)
    private String shipperRepresent;
    @Column(nullable = false)
    private String shipperNote;
    @Column(nullable = false)
    private String shipperPhone;
    private String isTruck;

    public ContainerTaskPickup() {
    }

    public ContainerTaskPickup(String pickupDoNumber, String pickupBookingNumber, String pickupCtnNumber, LocalDate pickupExpDate, LocalDate pickupExpCtnDate, String shipperName, String shipperRepresent, String shipperNote, String shipperPhone, String isTruck) {
        this.pickupDoNumber = pickupDoNumber;
        this.pickupBookingNumber = pickupBookingNumber;
        this.pickupCtnNumber = pickupCtnNumber;
        this.pickupExpDate = pickupExpDate;
        this.pickupExpCtnDate = pickupExpCtnDate;
        this.shipperName = shipperName;
        this.shipperRepresent = shipperRepresent;
        this.shipperNote = shipperNote;
        this.shipperPhone = shipperPhone;
        this.isTruck = isTruck;
    }

}




