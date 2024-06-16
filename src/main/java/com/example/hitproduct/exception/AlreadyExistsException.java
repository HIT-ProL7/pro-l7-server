package com.example.hitproduct.exception;
/*
 * @author HongAnh
 * @created 14 / 06 / 2024 - 6:57 PM
 * @project HitProduct
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
public class AlreadyExistsException extends RuntimeException {
    String message;
    HttpStatus status;
    String[] params;

    public AlreadyExistsException(String message) {
        super(message);
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
    }

    public AlreadyExistsException(String message, String message1, HttpStatus status, String[] params) {
        super(message);
        this.message = message1;
        this.status = status;
        this.params = params;
    }

    public AlreadyExistsException(String message, String[] params) {
        super(message);
        this.message = message;
        this.params = params;
        this.status = HttpStatus.BAD_REQUEST;
    }
}
