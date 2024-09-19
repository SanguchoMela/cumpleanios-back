package com.example.cumpleanios_back.infrastructure.dto.user;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserCreateDtoRequest(String name, String lastName, LocalDate birthDate, String email, String phone) {
}
