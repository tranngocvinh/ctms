package com.ctms.entity;

public record CustomerUpdateRequest(
        String name,
        String email,
        String password
) {
}
