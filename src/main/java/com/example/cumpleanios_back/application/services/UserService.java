package com.example.cumpleanios_back.application.services;

import com.example.cumpleanios_back.domain.entities.UserEntity;

import java.util.Optional;
import java.util.List;

public interface UserService  {
    Optional<UserEntity> findByID(long id);

    List<UserEntity> indexUsers();
    UserEntity showUser(Long id);
    UserEntity createUser(UserEntity users);
    UserEntity updateUser(Long id, UserEntity users);
    void deleteUser(Long id);


}
