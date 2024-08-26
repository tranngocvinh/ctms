package com.ctms.service;

import com.ctms.repository.CustomerRepository;
import com.ctms.dto.CustomerDTO;
import com.ctms.entity.Customer;
import com.ctms.entity.CustomerRegistrationRequest;
import com.ctms.entity.CustomerUpdateRequest;
import com.ctms.mapper.CustomerDTOMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerDTOMapper customerDTOMapper;
    private final JavaMailSender mailSender;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder, CustomerDTOMapper customerDTOMapper, CustomerDTOMapper customerDTOMapper1, JavaMailSender mailSender) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerDTOMapper = customerDTOMapper1;
        this.mailSender = mailSender;
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

    public void addAdmin(CustomerRegistrationRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setRoles(List.of("ADMIN"));
        customer.setEmail(request.email());
        customer.setPassword(passwordEncoder.encode(request.password()));
        customerRepository.save(customer);
    }

    public void addCustomer(CustomerRegistrationRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setRoles(List.of("CUSTOMER"));
        customer.setEmail(request.email());
        customer.setPassword(passwordEncoder.encode(request.password()));
        customerRepository.save(customer);
    }

    public void addManager(CustomerRegistrationRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setRoles(List.of("MANAGER"));
        customer.setEmail(request.email());
        customer.setPassword(passwordEncoder.encode(request.password()));
        customerRepository.save(customer);
    }

    public void addStaff(CustomerRegistrationRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setRoles(List.of("STAFF"));
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
        customer.setPassword(passwordEncoder.encode(customerUpdateRequest.password()));
        customerRepository.save(customer);
    }

    public Customer getCurrentCustomer() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException("Authentication information not found");
        }

        return (Customer) authentication.getPrincipal();
    }


    public Double getTotalUser() {
        return customerRepository.getTotalUser() ;
    }

    @Transactional
    public void generatePasswordResetToken(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        String token = UUID.randomUUID().toString();
        customer.setResetPasswordToken(token);
        customer.setTokenExpirationDate(LocalDateTime.now().plusHours(1)); // Token sẽ hết hạn sau 1 giờ
        customerRepository.save(customer);

        sendPasswordResetEmail(customer.getEmail(), token);
    }

    private void sendPasswordResetEmail(String email, String token) {
        String resetUrl = "https://g42.biz/auth/register?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Yêu cầu cấp lại mật khẩu");
        message.setText("Chào bạn,\n\nVui lòng bấm vào liên kết sau để tạo lại mật khẩu:\n" + resetUrl +
                "\n\nLiên kết này sẽ hết hạn trong vòng 24 giờ.");

        mailSender.send(message);
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        Customer customer = customerRepository.findByResetPasswordToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (customer.getTokenExpirationDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token has expired");
        }

        customer.setPassword(passwordEncoder.encode(newPassword));
        customer.setResetPasswordToken(null);  // Xóa token sau khi dùng xong
        customer.setTokenExpirationDate(null);
        customerRepository.save(customer);
    }
}


