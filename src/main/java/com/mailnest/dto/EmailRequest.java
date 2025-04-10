package com.mailnest.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmailRequest {

    private String to;

    private String subject;

    private String message;
}
