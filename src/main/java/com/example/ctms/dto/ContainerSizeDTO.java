package com.example.ctms.dto;

public record ContainerSizeDTO(
        Integer id,
        Double length,
        Double width,
        Double height,
        Double volume,
        Double weight,
        Double loadCapacity,
        Double maxLoad,
        ContainerTypeDTO containerType

) {}
