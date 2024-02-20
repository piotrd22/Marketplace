package com.example.marketplace.mappers;

import com.example.marketplace.dtos.request.auth.SignUpDto;
import com.example.marketplace.dtos.response.UserDto;
import com.example.marketplace.models.User;
import com.example.marketplace.security.services.UserDetailsImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

// I'm starting to map using my own methods, when there will be time I will change other MapStruct classes to my own mappers
@Component
public class UserMapper {

    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        List<String> roles = user.getRoles().stream()
                .map(role -> role.getName().name())
                .toList();
        userDto.setRoles(roles);
        return userDto;
    }

    public UserDto toDto(UserDetailsImpl userDetails) {
        UserDto userDto = new UserDto();
        userDto.setId(userDetails.getId());
        userDto.setEmail(userDetails.getEmail());
        userDto.setUsername(userDetails.getUsername());
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        userDto.setRoles(roles);
        return userDto;
    }

    public User toEntity(SignUpDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setUsername(dto.getUsername());
        return user;
    }
}
