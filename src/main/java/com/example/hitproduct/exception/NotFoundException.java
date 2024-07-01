package com.example.hitproduct.exception;
/*
 * @author HongAnh
 * @created 25 / 06 / 2024 - 8:16 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotFoundException extends RuntimeException {
    String message;
    HttpStatus status;
    String[] params;

    public NotFoundException(String message) {
        super(message);
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
    }

    public NotFoundException(String message, HttpStatus status, String[] params) {
        super(message);
        this.message = message;
        this.status = status;
        this.params = params;
    }

    public NotFoundException(String message, String[] params) {
        super(message);
        this.message = message;
        this.params = params;
        this.status = HttpStatus.BAD_REQUEST;
    }
}
