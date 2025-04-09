package com.mailnest.services.impl;

import com.mailnest.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String to, String subject, String message) {

       SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

       simpleMailMessage.setTo(to);
       simpleMailMessage.setSubject(subject);
       simpleMailMessage.setText(message);
       simpleMailMessage.setFrom("maimam8409@gmail.com");
       javaMailSender.send(simpleMailMessage);
       log.info("email send");
    }

    @Override
    public void sendEmail(String[] to, String subject, String message) {

    }

    @Override
    public void sendEmailWithHtml(String to, String subject, String htmlContent) {

    }

    @Override
    public void sendEmailWithFile(String to, String subject, String message, File file) {

    }
}
