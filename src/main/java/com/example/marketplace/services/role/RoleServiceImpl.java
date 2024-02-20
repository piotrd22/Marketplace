package com.example.marketplace.services.role;

import com.example.marketplace.enums.RoleName;
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
    public Role getRoleByName(RoleName name) {
        return roleRepository.findByName(name).orElseThrow(() -> new NotFoundException("Role with name '%s' not found.".formatted(name.name())));
    }
}
