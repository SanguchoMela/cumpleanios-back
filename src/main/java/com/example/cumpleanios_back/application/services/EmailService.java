package com.example.cumpleanios_back.application.services;

import com.example.cumpleanios_back.application.services.utils.EmailBody;

public interface EmailService {
    boolean sendEmail(EmailBody emailBody);
}
