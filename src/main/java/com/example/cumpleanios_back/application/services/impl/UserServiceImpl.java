package com.example.cumpleanios_back.application.services.impl;

import com.example.cumpleanios_back.application.services.UserService;
import com.example.cumpleanios_back.domain.entities.UserEntity;
import com.example.cumpleanios_back.domain.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
    public UserEntity updateUser(Long id, UserEntity users) {
        UserEntity usersBd = userRepository.findById(id).get();
        if (Objects.nonNull(users.getName())) {
            usersBd.setName(users.getName());
        }

        if (Objects.nonNull(users.getLastName())) {
            usersBd.setLastName(users.getLastName());
        }

        if (Objects.nonNull(users.getEmail())) {
            usersBd.setEmail(users.getEmail());
        }

        if (Objects.nonNull(users.getDateBirth())) {
            usersBd.setDateBirth(users.getDateBirth());
        }
        return this.userRepository.save(usersBd);
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
        System.out.println(birthDate);
        System.out.println(LocalDate.now());
        System.out.println(Period.between(birthDate, LocalDate.now()).getYears());
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

}