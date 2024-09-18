package com.example.cumpleanios_back.application.services.impl;

import com.example.cumpleanios_back.application.services.EmailService;
import com.example.cumpleanios_back.application.services.utils.EmailBody;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender sender;

    public EmailServiceImpl(JavaMailSender sender) {
        this.sender = sender;
    }

    @Override
    public boolean sendEmail(EmailBody emailBody) {
        return sendEmailTool(emailBody.content(),emailBody.email(), emailBody.subject());
    }
    private boolean sendEmailTool(String textMessage, String email,String subject) {
        boolean send = false;
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(email);
            helper.setText(textMessage, true);
            helper.setSubject(subject);
            sender.send(message);
            send = true;
        } catch (MessagingException e) {
            System.out.println("Something went wrong during sending email "+ e.getMessage());
        }
        return send;
    }
}
