package com.example.ctms.auth;

public record AuthenticationRequest(
        String username,
        String password
) {
}
