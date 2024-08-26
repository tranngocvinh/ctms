package com.ctms.dto;

public record ContainerUpdateRequest(
        String name,
        String address,
        String phoneNumber,
        String email,
        String website,
        String detailService,
        byte[] imageData
) {

}
