package com.ctms.entity;

public record ResetPasswordRequest (
        String token,
        String newPassword
)  {
}
