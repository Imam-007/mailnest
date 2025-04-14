# 📧 MailNest Email Service

MailNest is a simple and extensible email service built with **Spring Boot**. It supports sending plain text emails, HTML emails, emails with attachments, and also provides functionality to fetch messages from an inbox.

---

## 🚀 Features

- Send plain text email to single or multiple recipients
- Send HTML email
- Send emails with file attachments (via File or InputStream)
- Read inbox emails and download attachments
- Standardized API response using `CustomResponse`
- Multipart email handling

---

## 🛠️ Technologies Used

- Java 17+
- Spring Boot
- Spring Mail (`JavaMailSender`)
- Jakarta Mail
- Lombok
- Maven

---

## 📬 API Endpoints

### `POST /api/v1/email/send`

Send a simple HTML email.

#### Request Body:
```json
{
  "to": "recipient@example.com",
  "subject": "Hello",
  "message": "<h1>Hello from MailNest!</h1>"
}
```
#### application.properties:
```json
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# For fetching inbox emails
mail.store.protocol=imaps
mail.imaps.host=imap.gmail.com
mail.imaps.port=993
```
``` bash

mvn clean install
mvn spring-boot:run

```