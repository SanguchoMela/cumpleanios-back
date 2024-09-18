package com.example.cumpleanios_back.infrastructure.dto.auth;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

@JsonPropertyOrder({"status", "message"})
@Builder
public record ErrorMessageResponse(String message, Boolean status) {
}
