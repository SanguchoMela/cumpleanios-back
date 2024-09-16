package com.example.cumpleanios_back.application.services;

import com.example.cumpleanios_back.domain.entities.User;
import com.example.cumpleanios_back.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByID(long id) {
        return this.userRepository.findById(id);
    }
}
