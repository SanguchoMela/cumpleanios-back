package com.example.cumpleanios_back.infrastructure.dto.auth;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"status", "message"})
public record ErrorMessageResponse(String message, Boolean status) {
}
