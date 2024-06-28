package com.example.hitproduct.domain.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.context.annotation.Bean;

@Getter
@Builder
public class MailDTO {
    String to;
    String subject;
    String text;
}
