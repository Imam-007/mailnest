package com.mailnest.services;

import java.io.File;
import java.io.InputStream;

public interface EmailService {

    //send email to single person
    void sendEmail(String to, String subject, String message);

    //send email to multiple person
    void sendEmail(String[] to, String subject, String message);

    //send email with HTML
    void sendEmailWithHtml(String to, String subject, String htmlContent);

    //send file in mail
    void sendEmailWithFile(String to, String subject, String message, File file);

    public void sendEmailWithFile(String to, String subject, String message, InputStream inputStream);
}
