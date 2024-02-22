package com.example.marketplace.services.user;

import com.example.marketplace.enums.RoleName;
import com.example.marketplace.exceptions.AlreadyExistsException;
import com.example.marketplace.exceptions.NotFoundException;
import com.example.marketplace.models.Role;
import com.example.marketplace.models.User;
import com.example.marketplace.repositories.UserRepository;
import com.example.marketplace.services.role.RoleService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new AlreadyExistsException("Email '%s' is already taken!".formatted(user.getEmail()));
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new AlreadyExistsException("Username '%s' is already taken!".formatted(user.getUsername()));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleService.getRoleByName(RoleName.ROLE_USER);
        user.getRoles().add(userRole);
        return userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User with username '%s' not found. ".formatted(username)));
    }
}
