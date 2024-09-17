package com.example.cumpleanios_back.application.services;

import com.example.cumpleanios_back.domain.entities.UserEntity;

import java.util.Optional;

public interface UserService  {
    Optional<UserEntity> findByID(long id);

}
