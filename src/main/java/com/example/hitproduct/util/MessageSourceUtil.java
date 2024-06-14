package com.example.hitproduct.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageSourceUtil {
    private final MessageSource messageSource;

    public String getLocalizedMessage(String message, Object... args) {
        return messageSource.getMessage(message, args, LocaleContextHolder.getLocale());
    }
}
