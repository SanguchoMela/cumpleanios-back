package com.example.cumpleanios_back.domain.repositories;

import com.example.cumpleanios_back.domain.entities.User;
public interface UserRepository{
    User findByID(long id );
}
