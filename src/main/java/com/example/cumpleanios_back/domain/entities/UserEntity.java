package com.example.cumpleanios_back.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name must not be empty")
    private String name;

    @Column(unique = true)
    private String username;

    @NotBlank(message = "LastName must not be empty")
    private String lastName;

    @Email(message = "Please provide a valid email!")
    @Column(unique = true)
    private String email;

    private String password;

    private String phone;

    @Past(message = "La fecha de nacimiento debe ser una fecha pasada.")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateBirth;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class, cascade = CascadeType.MERGE) // We need all roles
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
// Intermediate table configuration
    private Set<Role> roles = new HashSet<>();

}
