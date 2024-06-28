package com.example.hitproduct.job;

import com.example.hitproduct.domain.dto.MailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutoMailer {
    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender mailSender;

    public void send(MailDTO dto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(dto.getTo());
        message.setSubject(dto.getSubject());
        message.setText(dto.getText());
        new Thread(() -> mailSender.send(message)).start();
    }
}
