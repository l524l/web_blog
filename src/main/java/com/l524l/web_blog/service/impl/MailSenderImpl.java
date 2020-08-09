package com.l524l.web_blog.service.impl;

import com.l524l.web_blog.service.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderImpl implements MailSender {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String username;

    @Override
    public void sendEmail(String mailTO, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailMessage.setTo(mailTO);

        mailSender.send(mailMessage);

    }
}
