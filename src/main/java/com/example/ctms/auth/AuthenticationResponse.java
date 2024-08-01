package com.example.ctms.auth;

import com.example.ctms.dto.CustomerDTO;
import com.example.ctms.dto.CustomerDTO;

public record AuthenticationResponse (
        String token,
        CustomerDTO customerDTO
){

}
