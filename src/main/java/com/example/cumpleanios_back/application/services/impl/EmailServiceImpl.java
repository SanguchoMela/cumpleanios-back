package com.example.cumpleanios_back.application.services.impl;

import com.example.cumpleanios_back.application.services.EmailService;
import com.example.cumpleanios_back.application.services.utils.EmailBody;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;


@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender sender;
    private final SpringTemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender sender, SpringTemplateEngine templateEngine) {
        this.sender = sender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendEmail(EmailBody emailBody) {
        sendEmailTool(emailBody);
    }



    private void sendEmailTool(EmailBody emailBody) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {

            String htmlContent = templateEngine.process(emailBody.template(), emailBody.context());
            helper.setTo(emailBody.recipientList());
            helper.setText(htmlContent, true);
            helper.setSubject(emailBody.subject());
            sender.send(message);
        } catch (MessagingException e) {
            System.out.println("Something went wrong during sending email: " + e.getMessage());
        }
    }


}
