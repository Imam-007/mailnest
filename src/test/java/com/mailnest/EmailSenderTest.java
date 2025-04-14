package com.mailnest;

import com.mailnest.dto.Message;
import com.mailnest.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class EmailSenderTest {

    @Autowired
    private EmailService emailService;


    @Test
    void emailSend(){
        System.out.println("send email");
        emailService.sendEmail("imimam8409@gmail.com", "email from imam", "het how are you");
    }

    @Test
    void emailSends(){
        System.out.println("send email to multiple person");
        String[] email = {"imimam8409@gmail.com", "mdaurangzebimam15299@gmail.com"};
        emailService.sendEmail(email, "email from imam", "emil to mulitiple person");
    }

    @Test
    void sendHtml(){
        String html = "" +
                "<h1 style='color:red;'> Hii i am imam </h1>" +
                "";

        emailService.sendEmailWithHtml("imimam8409@gmail.com", "form imam", html);
    }

    @Test
    void getInbox() {
        List<Message> inboxMessage = emailService.getInboxMessage();

        inboxMessage.forEach(item -> {
            System.out.println(item.getSubject());
            System.out.println(item.getContent());
            System.out.println(item.getFiles());
            System.out.println("=====================================");
        });
    }
}
