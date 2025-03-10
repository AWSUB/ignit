package com.ignit.internship.service.auth;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public final class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(final JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(SimpleMailMessage mailMessage) {
        mailSender.send(mailMessage);
    }
}
