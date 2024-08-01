package com.example.ctms.dto;

import com.example.ctms.entity.ContainerSize;

public record EmptyContainerDetailDTO(
        ContainerSizeDTO containerSize,
        int quantity
){
}
