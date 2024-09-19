package com.example.cumpleanios_back.infrastructure.dto.user;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserBirtdhayDtoResponse(String name, String last_name, String email, LocalDate dateBirth, Integer age, String phone) {
}
