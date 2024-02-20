package com.example.marketplace.config.datainitialization;

import com.example.marketplace.enums.RoleName;
import com.example.marketplace.exceptions.AlreadyExistsException;
import com.example.marketplace.services.role.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class RoleInitializer implements CommandLineRunner, Ordered {


    private final Logger logger = LoggerFactory.getLogger(RoleInitializer.class);
    private final RoleService roleService;

    public RoleInitializer(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) {
        for (RoleName roleName : RoleName.values()) {
            try {
                roleService.addRole(roleName);
                logger.info("Role {} added.", roleName);
            } catch (AlreadyExistsException e) {
                logger.error("RoleInitializer error {}", e.getMessage());
            }
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
