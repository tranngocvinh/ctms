package com.ctms.auth;

import com.ctms.dto.CustomerDTO;

public record AuthenticationResponse (
        String token,
        CustomerDTO customerDTO
){

}
