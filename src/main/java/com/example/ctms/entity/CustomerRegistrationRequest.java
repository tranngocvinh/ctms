package com.example.ctms.entity;

public record CustomerRegistrationRequest(
        String name,
        String email,
        String password
) {
}
