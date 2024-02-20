package com.example.marketplace.controllers;

import com.example.marketplace.dtos.request.auth.SignInDto;
import com.example.marketplace.dtos.request.auth.SignUpDto;
import com.example.marketplace.dtos.response.AuthDto;
import com.example.marketplace.dtos.response.UserDto;
import com.example.marketplace.mappers.UserMapper;
import com.example.marketplace.models.User;
import com.example.marketplace.security.jwt.JwtUtils;
import com.example.marketplace.security.services.UserDetailsImpl;
import com.example.marketplace.services.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController extends AbstractControllerBase {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserService userService, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthDto> signIn(@RequestBody @Valid SignInDto dto) {
        logger.info("Inside: AuthController -> signIn()...");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        User user = getUser();
        AuthDto auth = new AuthDto();
        auth.setToken(jwt);
        UserDto userDto = userMapper.toDto(user);
        auth.setUser(userDto);
        return ResponseEntity.ok().body(auth);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody @Valid SignUpDto dto, HttpServletRequest request) {
        logger.info("Inside: AuthController -> signUp()...");
        User user = userMapper.toEntity(dto);
        user = userService.addUser(user);
        URI location = getURILocationFromRequest(user.getId(), request);
        return ResponseEntity.created(location).body(userMapper.toDto(user));
    }
}
