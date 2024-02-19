package com.example.marketplace.security.jwt;

import com.example.marketplace.security.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;

    public AuthTokenFilter(JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);

//            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
//
//            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (!StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return null;
        }

        return headerAuth.substring(7);
    }
}
