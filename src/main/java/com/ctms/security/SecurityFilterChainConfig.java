package com.ctms.security;

import com.ctms.jwt.JWTAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    public SecurityFilterChainConfig(AuthenticationProvider authenticationProvider, JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/**").permitAll()

//                        .requestMatchers("/api/proxy","/api/proxy/**").hasAnyAuthority("ADMIN", "MANAGER", "STAFF", "CUSTOMER")
//                        .requestMatchers("/api/ships","/api/ships/**").hasAnyAuthority("ADMIN", "MANAGER", "STAFF", "CUSTOMER")
//                        .requestMatchers("/api/containers","/api/containers/**").hasAnyAuthority("ADMIN", "MANAGER", "STAFF", "CUSTOMER")
//                        .requestMatchers("/api/v1/supplier","/api/v1/supplier/**").permitAll()
//                        .requestMatchers("/api/ports","/api/ports/**").hasAnyAuthority("ADMIN", "MANAGER", "STAFF", "CUSTOMER")
//                        .requestMatchers("/api/v1/customers/reset-password","/api/v1/customers/forgot-password").permitAll()
//                        .requestMatchers("/api/drop-orders/detfee/sum","/api/drop-orders/detfee/sum/**").hasAnyAuthority("ADMIN", "MANAGER", "STAFF")
//                        .requestMatchers("/api/v1/repair/cost/paid","/api/v1/repair/cost/paid/**").hasAnyAuthority("ADMIN", "MANAGER", "STAFF")
//                        .requestMatchers("/api/v1/auth","/api/v1/auth/**").permitAll()
//                        .requestMatchers("/api/shipSchedules").hasAnyAuthority("ADMIN", "MANAGER", "STAFF", "CUSTOMER")
//                        .requestMatchers("/api/v1/admin","/api/v1/admin/**").hasAuthority("ADMIN")
//                        .requestMatchers("/api/v1/customers","/api/v1/customers/**").hasAnyAuthority("ADMIN", "MANAGER")
//                        .requestMatchers("/api/containers/allocate/ship", "/api/containers/allocate/ship/**").hasAnyAuthority("MANAGER")
//                        .requestMatchers("/api/delivery-orders/total-amounts-by-month").hasAnyAuthority("ADMIN", "MANAGER", "STAFF")
//                        .requestMatchers("/api/drop-orders/detfee-count").hasAnyAuthority("ADMIN", "MANAGER", "STAFF")
//                        .requestMatchers("/api/v1/repair/repaircost-count").hasAnyAuthority("ADMIN", "MANAGER", "STAFF")
//                        .requestMatchers("/api/delivery-orders/cost/paid").hasAnyAuthority("ADMIN", "MANAGER", "STAFF")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

