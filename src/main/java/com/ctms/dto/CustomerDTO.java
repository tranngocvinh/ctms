package com.ctms.dto;

import java.util.List;

public record CustomerDTO(
        Integer id,
        String name,
        String email,
        String username,
        List<String> roles
) {
}
