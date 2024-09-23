package com.example.cumpleanios_back.application.services;

import com.example.cumpleanios_back.domain.entities.RoleType;
import com.example.cumpleanios_back.domain.entities.UserEntity;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

public interface UserService  {
    Optional<UserEntity> findByID(long id);

    List<UserEntity> indexUsers();
    UserEntity showUser(Long id);
    UserEntity createUser(UserEntity users);
    void deleteUser(Long id);
    int getAge(UserEntity user);
    List<UserEntity> findUsersBetweenPeriod(LocalDate startDate, LocalDate endDate);
    List<UserEntity> findAllByBirthMonth(Integer month);
    List<UserEntity> findByRoleType(RoleType roleType);
}
