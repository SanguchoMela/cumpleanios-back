package com.example.cumpleanios_back.application.services.utils;

import lombok.Builder;

@Builder
public record EmailBody(String[] recipientList, String content, String subject) {
}
