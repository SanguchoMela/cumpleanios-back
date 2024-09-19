package com.example.cumpleanios_back.domain.repositories;

import com.example.cumpleanios_back.domain.entities.RoleType;
import com.example.cumpleanios_back.domain.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);
    List<UserEntity> findAllByDateBirthBetween(LocalDate startDate, LocalDate endDate);
    @Query("SELECT u FROM UserEntity u WHERE EXTRACT(MONTH FROM u.dateBirth) = :month")
    List<UserEntity> findAllByBirthMonth(@Param("month") int month);
    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r.roleType = :roleType")
    List<UserEntity> findByRoleType(@Param("roleType") RoleType roleType);
}