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
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String FROM;

    public void send(MailDTO dto) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(FROM);
        message.setTo(dto.getTo());
        message.setSubject(dto.getSubject());
        message.setText("Your new password is: " + dto.getText());

        new Thread(() -> mailSender.send(message)).start();
    }
}
