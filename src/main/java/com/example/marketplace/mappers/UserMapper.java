package com.example.marketplace.mappers;

import com.example.marketplace.dtos.request.auth.SignUpDto;
import com.example.marketplace.dtos.response.UserDto;
import com.example.marketplace.models.User;
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
        userDto.setCreatedAt(user.getCreatedAt());
        userDto.setUpdatedAt(user.getUpdatedAt());
        List<String> roles = user.getRoles().stream()
                .map(role -> role.getName().name())
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
