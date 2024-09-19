package com.example.cumpleanios_back.application.services.impl;

import com.example.cumpleanios_back.application.services.RoleService;
import com.example.cumpleanios_back.domain.entities.Role;
import com.example.cumpleanios_back.domain.entities.RoleType;
import com.example.cumpleanios_back.domain.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> findByRoleType(RoleType roleType) {
        return roleRepository.findByRoleType(roleType);
    }

    @Override
    public Role getOrCreate(RoleType roleType) {
        return this.roleRepository.findByRoleType(roleType)
                .orElseGet(() -> this.roleRepository.save(Role.builder().roleType(roleType).build()));
    }
}
