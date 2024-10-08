package com.ctms.controller;

import com.ctms.service.CustomerService;
import com.ctms.dto.CustomerDTO;
import com.ctms.entity.CustomerRegistrationRequest;
import com.ctms.entity.CustomerUpdateRequest;
import com.ctms.entity.ResetPasswordRequest;
import com.ctms.jwt.JWTUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/manager")
    public ResponseEntity<?> registerManager(@RequestBody CustomerRegistrationRequest request) {
        customerService.addManager(request);
        String jwtToken = jwtUtil.issueToken(request.email(), "MANAGER");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .build();
    }

    @PostMapping("/staff")
    public ResponseEntity<?> registerStaff(@RequestBody CustomerRegistrationRequest request) {
        customerService.addStaff(request);
        String jwtToken = jwtUtil.issueToken(request.email(), "STAFF");
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

    @GetMapping("/count")
    public Double getTotalPaidRepairCost() {
        return customerService.getTotalUser();
    }

    @PostMapping("/forgot-password/{email}")
    public ResponseEntity<?> forgotPassword(@PathVariable String email) {
        customerService.generatePasswordResetToken(email);
        return ResponseEntity.ok("Password reset link has been sent to your email.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        customerService.resetPassword(request.token(), request.newPassword());
        return ResponseEntity.ok("Password has been reset successfully.");
    }
}
