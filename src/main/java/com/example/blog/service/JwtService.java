package com.example.blog.service;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;

/**
 * Service class for handling JWT (JSON Web Token) operations
 * Provides methods for token generation, validation, and extraction
 */
@Service
public class JwtService {
    // Secret key used for signing and verifying JWT tokens
    private String SecretKey;

    /**
     * Constructor that initializes the secret key when service is created
     */
    JwtService(){
        SecretKey = generateSecretkey();
    }

    /**
     * Generate a secure secret key for JWT signing
     * @return Base64 encoded secret key string
     */
    public String generateSecretkey(){
        try{
            // Create key generator for HMAC-SHA256 algorithm
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");

            // Generate a new secret key
            SecretKey secretKey = keyGenerator.generateKey();
            
            // Encode the key to Base64 string for storage
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        }
        catch(NoSuchAlgorithmException e){
            // Throw runtime exception if algorithm is not available
            throw new RuntimeException("Error generating secretkey", e);
        }
    }

    /**
     * Generate a JWT token for a given username
     * @param username - The username to include in the token
     * @return JWT token string
     */
    public String generateToken(String username){
        // Create empty claims map (can be extended with additional claims)
        Map<String,Object> claims = new HashMap<>();
        
        // Build JWT token with claims, subject, issued time, expiration, and signature
        return Jwts.builder()
                .setClaims(claims)                                    // Set custom claims
                .setSubject(username)                                 // Set username as subject
                .setIssuedAt(new Date(System.currentTimeMillis()))    // Set issued time
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24*4)) // Set expiration (4 days)
                .signWith(getKey(), SignatureAlgorithm.HS256)         // Sign with secret key
                .compact();                                           // Generate compact token string
    }

    /**
     * Get the signing key from the secret key string
     * @return Key object for JWT signing/verification
     */
    public Key getKey(){
        // Decode Base64 secret key to byte array
        byte[] keyBytes = Decoders.BASE64.decode(SecretKey);
        
        // Create HMAC-SHA key from byte array
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extract username from JWT token
     * @param token - The JWT token string
     * @return Username extracted from token subject
     */
    public String extractUsername(String token){
        // Extract subject claim which contains the username
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Generic method to extract any claim from JWT token
     * @param token - The JWT token string
     * @param claimsResolver - Function to extract specific claim
     * @return The extracted claim value
     */
    public <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver){
        // Extract all claims from token
        final Claims claims = extractAllClaims(token);
        
        // Apply the resolver function to get specific claim
        return claimsResolver.apply(claims);
    }

    /**
     * Extract all claims from JWT token
     * @param token - The JWT token string
     * @return Claims object containing all token claims
     */
    private Claims extractAllClaims(String token){
        // Parse and verify token, then extract claims
        return Jwts.parserBuilder()
                .setSigningKey(getKey())    // Set signing key for verification
                .build()
                .parseClaimsJws(token)      // Parse and verify token
                .getBody();                 // Get claims from token body
    }

    /**
     * Validate JWT token against user details
     * @param token - The JWT token to validate
     * @param userDetails - User details to validate against
     * @return true if token is valid, false otherwise
     */
    public boolean validateToken(String token, UserDetails userDetails){
        // Extract username from token
        final String userName = extractUsername(token);
        
        // Check if username matches and token is not expired
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Check if JWT token has expired
     * @param token - The JWT token to check
     * @return true if token is expired, false otherwise
     */
    public boolean isTokenExpired(String token){
        // Compare token expiration time with current time
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extract expiration date from JWT token
     * @param token - The JWT token string
     * @return Expiration date from token
     */
    private Date extractExpiration(String token){
        // Extract expiration claim from token
        return extractClaim(token, Claims::getExpiration);
    }
}
