package com.example.marketplace.config.datainitialization;

import com.example.marketplace.enums.RoleName;
import com.example.marketplace.exceptions.AlreadyExistsException;
import com.example.marketplace.models.Role;
import com.example.marketplace.models.User;
import com.example.marketplace.services.role.RoleService;
import com.example.marketplace.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class UserInitializer implements CommandLineRunner, Ordered {

    private final Logger logger = LoggerFactory.getLogger(UserInitializer.class);
    private final UserService userService;
    private final RoleService roleService;
    private final YamlUserInitializerProperties yamlUserInitializerProperties;

    public UserInitializer(UserService userService, RoleService roleService, YamlUserInitializerProperties yamlUserInitializerProperties) {
        this.userService = userService;
        this.roleService = roleService;
        this.yamlUserInitializerProperties = yamlUserInitializerProperties;
    }

    @Override
    public void run(String... args) {
        try {
            User user = new User();
            user.setPassword(yamlUserInitializerProperties.getPassword());
            user.setUsername(yamlUserInitializerProperties.getUsername());
            user.setEmail(yamlUserInitializerProperties.getEmail());
            Role role = roleService.getRoleByName(RoleName.ROLE_ADMIN);
            user.getRoles().add(role);
            userService.addUser(user);
            logger.info("User with username {} added.", user.getUsername());
        } catch (AlreadyExistsException e) {
            logger.error("UserInitializer error {}", e.getMessage());
        }
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
