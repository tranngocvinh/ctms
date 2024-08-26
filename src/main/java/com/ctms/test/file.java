package com.ctms.test;

import jakarta.persistence.*;

@Table
@Entity
public class file {
    public file() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    public file(String name,byte[] imageData) {
    this.name = name;
        this.imageData = imageData;
    }

    @Lob
    @Column(name = "imagedata",length = 1000)
    private byte[] imageData;
}
