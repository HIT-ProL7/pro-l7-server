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
import com.example.hitproduct.domain.dto.global.BlankData;
import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.global.Status;
import com.example.hitproduct.util.MessageSourceUtil;
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
    private final MessageSourceUtil messageSourceUtil;

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<RestData<?>> handleUserAlreadyExistsException(AlreadyExistsException ex){
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

    @ExceptionHandler(AppException.class)
    public ResponseEntity<GlobalResponse<Meta, BlankData>> handleAppException(AppException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GlobalResponse
                        .<Meta, BlankData>builder()
                        .meta(Meta.builder()
                                .status(Status.ERROR)
                                .message(messageSourceUtil.getLocalizedMessage(ex.getMessage()))
                                .build()
                        )
                        .build()
                );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<GlobalResponse<Meta, BlankData>> handleAppException(NotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GlobalResponse
                        .<Meta, BlankData>builder()
                        .meta(Meta.builder()
                                  .status(Status.ERROR)
                                  .message(messageSourceUtil.getLocalizedMessage(ex.getMessage()))
                                  .build()
                        )
                        .build()
                );
    }
}
