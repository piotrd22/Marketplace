package com.example.marketplace.services.role;

import com.example.marketplace.enums.RoleName;
import com.example.marketplace.exceptions.AlreadyExistsException;
import com.example.marketplace.exceptions.NotFoundException;
import com.example.marketplace.models.Role;
import com.example.marketplace.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleByName(RoleName roleName) {
        return roleRepository.findByName(roleName).orElseThrow(() -> new NotFoundException("Role with name '%s' not found.".formatted(roleName.name())));
    }

    @Override
    public Role addRole(RoleName roleName) {
        if (roleRepository.existsByName(roleName)) {
            throw new AlreadyExistsException("Role with name '%s' already exists".formatted(roleName.toString()));
        }
        Role role = new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }
}
