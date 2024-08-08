package com.example.ctms;

import com.example.ctms.dto.CustomerDTO;
import com.example.ctms.entity.Customer;
import com.example.ctms.entity.CustomerRegistrationRequest;
import com.example.ctms.entity.CustomerUpdateRequest;
import com.example.ctms.repository.CustomerRepository;
import com.example.ctms.mapper.CustomerDTOMapper;
import com.example.ctms.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CustomerDTOMapper customerDTOMapper;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private CustomerDTO customerDTO;
    private CustomerRegistrationRequest registrationRequest;
    private CustomerUpdateRequest updateRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = new Customer();
        customer.setId(1);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPassword("encodedPassword");
        customer.setRoles(List.of("ADMIN"));

        customerDTO = new CustomerDTO(1, "John Doe", "john.doe@example.com","encodedPassword", Collections.singletonList("ADMIN"));

        registrationRequest = new CustomerRegistrationRequest("John Doe", "john.doe@example.com", "password");

        updateRequest = new CustomerUpdateRequest("John Doe Updated", "john.doe.updated@example.com");
    }

    @Test
    public void testGetAllCustomers() {
        // Arrange
        when(customerRepository.findAll()).thenReturn(List.of(customer));
        when(customerDTOMapper.apply(customer)).thenReturn(customerDTO);

        // Act
        List<CustomerDTO> result = customerService.getAllCustomers();

        // Assert
        assertEquals(1, result.size());
        assertEquals(customerDTO, result.get(0));
    }

    @Test
    public void testGetCustomer() {
        // Arrange
        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customer));
        when(customerDTOMapper.apply(customer)).thenReturn(customerDTO);

        // Act
        CustomerDTO result = customerService.getCustomer(1);

        // Assert
        assertEquals(customerDTO, result);
    }

    @Test
    public void testGetCustomerNotFound() {
        // Arrange
        when(customerRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> customerService.getCustomer(1));
        assertEquals("Customer not found", thrown.getMessage());
    }

    @Test
    public void testAddCustomer() {
        // Arrange
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Act
        customerService.addCustomer(registrationRequest);

        // Assert
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void testUpdateCustomer() {
        // Arrange
        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Act
        customerService.update(1, updateRequest);

        // Assert
        verify(customerRepository, times(1)).save(any(Customer.class));
        assertEquals(updateRequest.name(), customer.getName());
        assertEquals(updateRequest.email(), customer.getEmail());
    }

    @Test
    public void testUpdateCustomerNotFound() {
        // Arrange
        when(customerRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> customerService.update(1, updateRequest));
        assertEquals("Customer not found", thrown.getMessage());
    }

    @Test
    public void testGetCurrentCustomer() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(customer);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Act
        Customer result = customerService.getCurrentCustomer();

        // Assert
        assertEquals(customer, result);
    }

    @Test
    public void testGetCurrentCustomerNotAuthenticated() {
        // Arrange
        SecurityContextHolder.getContext().setAuthentication(null);

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> customerService.getCurrentCustomer());
        assertEquals("Authentication information not found", thrown.getMessage());
    }
}
