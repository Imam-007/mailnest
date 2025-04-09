package com.mailnest;

import com.mailnest.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailSenderTest {

    @Autowired
    private EmailService emailService;


    @Test
    void emailSend(){
        System.out.println("send email");
        emailService.sendEmail("imimam8409@gmail.com", "email from imam", "het how are you");
    }
}
