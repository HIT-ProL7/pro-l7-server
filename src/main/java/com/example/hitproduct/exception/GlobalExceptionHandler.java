package com.example.hitproduct.exception;
/*
 * @author HongAnh
 * @created 14 / 06 / 2024 - 7:00 PM
 * @project HitProduct
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.base.RestData;
import com.example.hitproduct.base.VsResponseUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<RestData<?>> handleUserAlreadyExistsException(UserAlreadyExistsException ex){
        String message = messageSource.getMessage(ex.getMessage(), ex.getParams(), LocaleContextHolder.getLocale());
        log.error(message, ex);
        return VsResponseUtil.error(ex.getStatus(), message);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<RestData<?>> handleUserNotFoundException(UsernameNotFoundException ex){
        String message = messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());
        log.error(message, ex);
        return VsResponseUtil.error(HttpStatus.BAD_REQUEST, message);
    }
}
