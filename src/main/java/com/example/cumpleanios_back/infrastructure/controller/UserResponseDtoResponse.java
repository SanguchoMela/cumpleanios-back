package com.example.cumpleanios_back.infrastructure.controller;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserResponseDtoResponse(long id,String name, String lastName, LocalDate birthDate, String email){
}
