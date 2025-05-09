package com.mailnest.services.impl;

import com.mailnest.dto.Message;
import com.mailnest.services.EmailService;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${mail.store.protocol}")
    String protocol;

    @Value("${mail.imaps.host}")
    String host;

    @Value("${mail.imaps.port}")
    String port;

    @Value("${spring.mail.username}")
    String userName;

    @Value("${spring.mail.password}")
    String password;

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

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom("maimam8409@gmail.com");
        javaMailSender.send(simpleMailMessage);
        log.info("send email to multiple person");

    }

    @Override
    public void sendEmailWithHtml(String to, String subject, String htmlContent) {

        MimeMessage simpleMailMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(simpleMailMessage, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("maimam8409@gmail.com");
            helper.setText(htmlContent, true);
            javaMailSender.send(simpleMailMessage);
            log.info("send html content");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendEmailWithFile(String to, String subject, String message, File file) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("imimam8409@gmail.com");
            helper.setTo(to);
            helper.setText(message);
            helper.setSubject(subject);
            FileSystemResource fileSystemResource = new FileSystemResource(file);
            helper.addAttachment(fileSystemResource.getFilename(), file);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendEmailWithFile(String to, String subject, String message, InputStream inputStream) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("imimam8409@gmail.com");
            helper.setTo(to);
            helper.setText(message, true);
            helper.setSubject(subject);
            File file = new File("test.png");
            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            FileSystemResource fileSystemResource = new FileSystemResource(file);
            helper.addAttachment(fileSystemResource.getFilename(), file);
            javaMailSender.send(mimeMessage);
            log.info("send mail through input stream");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Message> getInboxMessage() {

        Properties configuration = new Properties();

        configuration.setProperty("mail.store.protocol", protocol);
        configuration.setProperty("mail.imaps.host", host);
        configuration.setProperty("mail.imaps.port", port);

        Session session = Session.getDefaultInstance(configuration);

        try {
            Store store = session.getStore();
            store.connect(userName, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            jakarta.mail.Message[] messages = inbox.getMessages();
            List<Message> list = new ArrayList<>();

            for (jakarta.mail.Message message : messages){

                String content = getContentFromEmailMessage(message);
                List<String> file = getFilesFromEmailMessage(message);
                list.add(Message.builder().subject(message.getSubject()).content(content).files(file).build());
            }
            return list;

        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getFilesFromEmailMessage(jakarta.mail.Message message) throws MessagingException, IOException {

        List<String> files = new ArrayList<>();
        if(message.isMimeType("multipart/*")){
            Multipart content = (Multipart) message.getContent();

            for (int i = 0; i < content.getCount(); i++) {
                BodyPart bodyPart = content.getBodyPart(i);
                if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                    InputStream inputStream = bodyPart.getInputStream();
                    String directoryPath = "src/main/resources/email";
                    Files.createDirectories(Paths.get(directoryPath)); // Ensure the folder exists

                    File file = new File(directoryPath + "/" + bodyPart.getFileName());

                    //saved the files
                    Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    //urls
                    files.add(file.getAbsolutePath());
                }
            }
        }
        return files;
    }

    private String getContentFromEmailMessage(jakarta.mail.Message message) throws MessagingException, IOException {

        if (message.isMimeType("text.plain") || message.isMimeType("text/html")) {
            return (String) message.getContent();
        } else if (message.isMimeType("multipart/*")) {
            Multipart part = (Multipart) message.getContent();
            for (int i = 0; i < part.getCount(); i++) {
                BodyPart bodyPart = part.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain")) {
                    return (String) bodyPart.getContent();
                }
            }
        }
        return "";
    }
}
