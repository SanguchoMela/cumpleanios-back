package com.example.cumpleanios_back.application.services.utils;

import lombok.Builder;
import org.thymeleaf.context.Context;

@Builder
public record EmailBody(String[] recipientList, String subject, Context context, String template) {
}
