package com.example.cumpleanios_back.application.services.impl;

import com.example.cumpleanios_back.application.services.EmailService;
import com.example.cumpleanios_back.application.services.utils.EmailBody;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Arrays;
import java.util.List;


@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender sender;
    private final SpringTemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender sender, SpringTemplateEngine templateEngine) {
        this.sender = sender;
        this.templateEngine = templateEngine;
    }

    @Override
    public boolean sendEmail(EmailBody emailBody) {
        return sendEmailTool(emailBody, emailBody.recipientList(), emailBody.subject());
    }

    private boolean sendEmailTool(EmailBody emailBody, String[] email, String subject) {
        boolean send = false;
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            Context context = new Context();
            context.setVariable("subject", subject);
            context.setVariable("content", emailBody.content());

            String htmlContent = templateEngine.process("emailTemplate", context);

            helper.setTo(email);
            helper.setText(htmlContent, true);
            helper.setSubject(subject);

            sender.send(message);
            send = true;
        } catch (MessagingException e) {
            System.out.println("Something went wrong during sending email: " + e.getMessage());
        }
        return send;
    }


}
