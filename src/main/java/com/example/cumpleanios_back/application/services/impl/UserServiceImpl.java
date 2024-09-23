package com.example.cumpleanios_back.application.services.impl;

import com.example.cumpleanios_back.application.services.UserService;
import com.example.cumpleanios_back.domain.entities.RoleType;
import com.example.cumpleanios_back.domain.entities.UserEntity;
import com.example.cumpleanios_back.domain.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserEntity> findByID(long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public List<UserEntity> indexUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public UserEntity showUser(Long id) {
        return this.userRepository.findById(id).get();
    }

    @Override
    public UserEntity createUser(UserEntity users) {
        return this.userRepository.save(users);
    }

    @Override
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public int getAge(UserEntity user) {
        LocalDate birthDate = user.getDateBirth();
        if (birthDate == null) {
            throw new IllegalArgumentException("Date of birth must not be empty");
        }
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    @Override
    public List<UserEntity> findUsersBetweenPeriod(LocalDate startDate, LocalDate endDate) {
        return this.userRepository.findAllByDateBirthBetween(startDate,endDate);
    }

    @Override
    public List<UserEntity> findAllByBirthMonth(Integer month) {
        return this.userRepository.findAllByBirthMonth(month);
    }

    @Override
    public List<UserEntity> findByRoleType(RoleType roleType) {
        return this.userRepository.findByRoleType(roleType);
    }

}