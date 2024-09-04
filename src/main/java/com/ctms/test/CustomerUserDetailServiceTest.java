package com.ctms.test;

import com.ctms.entity.Customer;
import com.ctms.repository.CustomerRepository;
import com.ctms.service.CustomerUserDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerUserDetailServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerUserDetailService customerUserDetailService;

    private Customer customer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = new Customer();
        customer.setId(1);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPassword("encodedPassword");
        customer.setRoles(List.of("ADMIN"));
    }

    @Test
    public void testLoadUserByUsername_UserFound() {
        // Arrange
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.of(customer));

        // Act
        UserDetails userDetails = customerUserDetailService.loadUserByUsername("john.doe@example.com");

        // Assert
        assertNotNull(userDetails);
        assertEquals(customer.getEmail(), userDetails.getUsername());
        assertEquals(customer.getPassword(), userDetails.getPassword());
        verify(customerRepository, times(1)).findByEmail("john.doe@example.com");
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Arrange
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException thrown = assertThrows(UsernameNotFoundException.class,
                () -> customerUserDetailService.loadUserByUsername("nonexistent@example.com"));
        assertEquals("User not found", thrown.getMessage());
        verify(customerRepository, times(1)).findByEmail("nonexistent@example.com");
    }
}