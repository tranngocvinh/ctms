package com.example.ctms.controller;

import com.example.ctms.dto.CustomerDTO;
import com.example.ctms.entity.CustomerRegistrationRequest;
import com.example.ctms.entity.CustomerUpdateRequest;
import com.example.ctms.jwt.JWTUtil;
import com.example.ctms.service.CustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final JWTUtil jwtUtil;

    public CustomerController(CustomerService customerService, JWTUtil jwtUtil) {
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public List<CustomerDTO> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("{customerId}")
    public CustomerDTO getCustomer(@PathVariable("customerId") Integer customerId) {
        return customerService.getCustomer(customerId);
    }

    @PostMapping("/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody CustomerRegistrationRequest request) {
        customerService.addAdmin(request);
        String jwtToken = jwtUtil.issueToken(request.email(), "ADMIN");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .build();
    }

    @PostMapping("/customer")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegistrationRequest request) {
        customerService.addCustomer(request);
        String jwtToken = jwtUtil.issueToken(request.email(), "CUSTOMER");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .build();
    }

    @PostMapping("/ship")
    public ResponseEntity<?> registerShip(@RequestBody CustomerRegistrationRequest request) {
        customerService.addShip(request);
        String jwtToken = jwtUtil.issueToken(request.email(), "SHIP");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .build();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Integer id) {
        customerService.delete(id);
    }

    @PutMapping("{id}")
    public void update(@PathVariable("id") Integer id, @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        customerService.update(id, customerUpdateRequest);
    }
}
