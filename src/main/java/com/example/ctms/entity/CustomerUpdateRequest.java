package com.example.ctms.entity;

public record CustomerUpdateRequest(
        String name,
        String email
) {
}
