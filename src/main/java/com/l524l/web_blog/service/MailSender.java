package com.l524l.web_blog.service;

public interface MailSender {
    public void sendEmail(String mailTO, String subject, String message);
}
