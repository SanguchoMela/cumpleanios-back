package com.example.cumpleanios_back.application.services;

import com.example.cumpleanios_back.domain.entities.User;

import java.util.Optional;

public interface UserService  {
    Optional<User> findByID(long id);

}
