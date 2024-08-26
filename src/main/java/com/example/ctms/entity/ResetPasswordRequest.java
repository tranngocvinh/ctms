package com.example.ctms.entity;

public record ResetPasswordRequest (
        String token,
        String newPassword
)  {
}
