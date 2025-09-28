package com.example.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.blog.service.JwtService;
import com.example.blog.service.MyUserDetailsService;

/**
 * JWT Authentication Filter
 * This filter intercepts HTTP requests and validates JWT tokens for authentication
 * Extends OncePerRequestFilter to ensure it runs only once per request
 */
@Component
public class Jwtfilter extends OncePerRequestFilter{

    // Inject JWT service for token operations
    @Autowired
    private JwtService jwtService;

    // Inject application context to get beans
    @Autowired
    ApplicationContext context;

    /**
     * Main filter method that processes each HTTP request
     * Extracts JWT token from Authorization header and validates it
     * 
     * @param request - HTTP request object
     * @param response - HTTP response object  
     * @param filterChain - Filter chain to continue processing
     * @throws ServletException if servlet error occurs
     * @throws IOException if I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        // Get Authorization header from request
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // Check if Authorization header exists and starts with "Bearer "
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            // Extract token by removing "Bearer " prefix (7 characters)
            token = authHeader.substring(7);
            
            // Extract username from JWT token
            username = jwtService.extractUsername(token);
            System.out.println("Extracted username: " + username); // Debug output
        }

        // If username is extracted and no authentication exists in security context
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            // Load user details from database using username
            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
            
            // Validate the JWT token against user details
            if(jwtService.validateToken(token, userDetails)){
                // Create authentication token with user details and authorities
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
                );
                
                // Set authentication details from request
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Set authentication in security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            System.out.println("authorities " + userDetails.getAuthorities()); // Debug output
        }
        
        // Get current authentication for debugging (commented out)
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("Auth after filter: " + auth);
//        System.out.println("Authorities after filter: " + auth.getAuthorities());
//        System.out.println("Is authenticated: " + auth.isAuthenticated());

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
