package com.example.cumpleanios_back.application.services.utils;

import lombok.Builder;

@Builder
public record EmailBody(String email, String content, String subject) {
}
