package com.example.hitproduct.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MailDTO {
    private String to;
    private String subject;
    private String text;
}
