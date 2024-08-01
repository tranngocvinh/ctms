package com.example.ctms.service;
import com.example.ctms.mapper.*;
import com.example.ctms.dto.CustomerDTO;
import com.example.ctms.entity.Customer;
import com.example.ctms.entity.CustomerRegistrationRequest;
import com.example.ctms.entity.CustomerUpdateRequest;
import com.example.ctms.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerDTOMapper customerDTOMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder, CustomerDTOMapper customerDTOMapper, CustomerDTOMapper customerDTOMapper1) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerDTOMapper = customerDTOMapper1;
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerDTOMapper)
                .toList();
    }

    public CustomerDTO getCustomer(Integer customerId) {
        return customerRepository.findById(customerId)
                .map(customerDTOMapper)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public void addCustomer(CustomerRegistrationRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setPassword(passwordEncoder.encode(request.password()));
        customerRepository.save(customer);
    }

    public void delete(Integer id) {
        customerRepository.deleteById(id);
    }

    public void update(Integer id, CustomerUpdateRequest customerUpdateRequest) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setName(customerUpdateRequest.name());
        customer.setEmail(customerUpdateRequest.email());
        customerRepository.save(customer);
    }

    public Customer getCurrentCustomer() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException("Authentication information not found");
        }

        return (Customer) authentication.getPrincipal();
    }
}


