package com.example.marketplace.controllers;

import com.example.marketplace.models.User;
import com.example.marketplace.security.services.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public abstract class AbstractControllerBase {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected URI getURILocationFromRequest(Long id, HttpServletRequest request) {
        return ServletUriComponentsBuilder
                .fromRequest(request)
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    // Mock -- Normally I would take the user out of security context
    protected Long getUserId() {
        return 1L;
    }

    protected User getUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }
}
