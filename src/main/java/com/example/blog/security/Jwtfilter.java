package com.example.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import jakarta.persistence.Access;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.blog.service.JwtService;
import com.example.blog.service.MyUserDetailsService;
@Component
public class Jwtfilter  extends OncePerRequestFilter{


    @Autowired
    private JwtService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        String  authHeader=request.getHeader("Authorization");
        String token=null;
        String username=null;

        if(authHeader!=null && authHeader.startsWith("Bearer ")){

            token=authHeader.substring(7);
            username =jwtService.extractUsername(token);
            System.out.println("Extracted username: " + username); // debug

        }

        if(username !=null&&SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails=context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
            if(jwtService.validateToken(token , userDetails)){
                UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            System.out.println("authorities "+userDetails.getAuthorities());
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("Auth after filter: " + auth);
//        System.out.println("Authorities after filter: " + auth.getAuthorities());
//        System.out.println("Is authenticated: " + auth.isAuthenticated());


        filterChain.doFilter(request,response);
    }



}
