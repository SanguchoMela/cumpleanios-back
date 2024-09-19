package com.example.cumpleanios_back.application.services;

import com.example.cumpleanios_back.domain.entities.Role;
import com.example.cumpleanios_back.domain.entities.RoleType;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByRoleType(RoleType roleType);

    Role getOrCreate(RoleType roleType);

}
