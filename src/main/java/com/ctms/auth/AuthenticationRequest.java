package com.ctms.auth;

public record AuthenticationRequest(
        String username,
        String password
) {
}
