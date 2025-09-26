package com.example.blog.security;


import java.net.http.HttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.blog.service.MyUserDetailsService;

import lombok.AllArgsConstructor;

/**
 * Security configuration class for Spring Security
 * Configures authentication, authorization, and security filters
 */
@Configuration
@EnableWebSecurity          // Enable Spring Security web security
@EnableMethodSecurity       // Enable method-level security annotations
@AllArgsConstructor
public class securityConfig {

    // Inject JWT filter for token-based authentication
    @Autowired
    public Jwtfilter jwtfilter;
    
    // Inject custom user details service for loading user information
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    /**
     * Configure authentication provider for database-based authentication
     * @return AuthenticationProvider configured with user details service and password encoder
     */
    @Bean
    public AuthenticationProvider authProvider(){
        // Create DAO authentication provider for database authentication
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        
        // Set custom user details service to load users from database
        provider.setUserDetailsService(myUserDetailsService);
        
        // Set BCrypt password encoder with strength 12 for secure password hashing
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        
        return provider;
    }

    /**
     * Configure security filter chain with authentication and authorization rules
     * @param http - HttpSecurity object to configure
     * @return SecurityFilterChain with configured security rules
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        // Configure HTTP security
        http.csrf(customizer -> customizer.disable())  // Disable CSRF for REST API
        .authorizeHttpRequests(request -> request
         .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()  // Allow OPTIONS requests for CORS
         .requestMatchers("login").permitAll()                    // Allow login endpoint without authentication
         .requestMatchers("r").permitAll()                        // Allow registration endpoint without authentication
         .requestMatchers("/**").authenticated()                  // Require authentication for all other endpoints
        );

        // Set authentication provider for user authentication
        http.authenticationProvider(authProvider());
        
        // Add JWT filter before username/password authentication filter
        http.addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }

    /**
     * Configure authentication manager for handling authentication requests
     * @param configuration - AuthenticationConfiguration object
     * @return AuthenticationManager for processing authentication
     * @throws Exception if configuration fails
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        // Get authentication manager from configuration
        return configuration.getAuthenticationManager();
    }
}
