package com.mailnest.controllers;

import com.mailnest.dto.CustomResponse;
import com.mailnest.dto.EmailResquest;
import com.mailnest.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<?> sendEmail(@RequestBody EmailResquest emailResquest){
        emailService.sendEmailWithHtml(emailResquest.getTo(),
                                       emailResquest.getSubject(),
                                       emailResquest.getMessage()
        );

        return ResponseEntity.ok(
                CustomResponse.builder()
                        .message("Email send Successdull")
                        .httpStatus(HttpStatus.OK)
                        .success(true).build()
        );
    }
    @PostMapping("/send-with-file")
    public ResponseEntity<?> sendWithFile(@RequestPart EmailResquest emailResquest, @RequestPart MultipartFile file) throws IOException {
        emailService.sendEmailWithFile(emailResquest.getTo(),
                                        emailResquest.getSubject(),
                                        emailResquest.getMessage(),
                                        file.getInputStream()
        );

        return ResponseEntity.ok(
                CustomResponse.builder()
                        .message("Email send Successfully")
                        .httpStatus(HttpStatus.OK)
                        .success(true).build()
        );
    }
}
