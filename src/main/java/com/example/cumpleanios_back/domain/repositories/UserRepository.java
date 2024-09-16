package com.example.cumpleanios_back.domain.repositories;

import com.example.cumpleanios_back.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
