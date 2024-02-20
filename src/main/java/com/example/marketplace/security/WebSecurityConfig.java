package com.example.marketplace.security;

import com.example.marketplace.enums.RoleName;
import com.example.marketplace.security.jwt.AuthEntryPointJwt;
import com.example.marketplace.security.jwt.AuthTokenFilter;
import com.example.marketplace.security.jwt.JwtUtils;
import com.example.marketplace.security.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final AuthEntryPointJwt unauthorizedHandler;

    // I remove the ROLE_ prefix because Spring adds this prefix itself when checking the role
    private final static String ROLE_PREFIX = "ROLE_";
    private final static String USER_ROLE = RoleName.ROLE_USER.name().replace(ROLE_PREFIX, "");
    private final static String ADMIN_ROLE = RoleName.ROLE_ADMIN.name().replace(ROLE_PREFIX, "");

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, AuthEntryPointJwt unauthorizedHandler) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsService) {
        return new AuthTokenFilter(jwtUtils, userDetailsService);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsService) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authenticationJwtTokenFilter(jwtUtils, userDetailsService), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(req -> req
                        // CategoryController
                        .requestMatchers(HttpMethod.POST, "/api/categories/**").hasAnyRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.DELETE, "/api/categories/**").hasAnyRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.PUT, "/api/categories/**").hasAnyRole(ADMIN_ROLE)
                        // ProductController
                        .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasAnyRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.PUT, "/api/products/**").hasAnyRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.POST, "/api/products").hasAnyRole(ADMIN_ROLE)
                        // CartController
                        .requestMatchers("/api/cart/**").hasAnyRole(USER_ROLE)
                        // OrderController
                        .requestMatchers(HttpMethod.GET, "/api/orders").hasAnyRole(ADMIN_ROLE)
                        .requestMatchers("/api/orders/**").hasAnyRole(USER_ROLE)
                        // All
                        .anyRequest().permitAll()
                )
                .build();
    }
}
