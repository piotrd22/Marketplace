package com.example.marketplace.services.role;

import com.example.marketplace.enums.RoleName;
import com.example.marketplace.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> getRoles();
    Role getRoleByName(RoleName roleName);
    Role addRole(RoleName roleName);
}
