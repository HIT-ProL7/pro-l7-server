package com.example.hitproduct.exception;
/*
 * @author HongAnh
 * @created 21 / 06 / 2024 - 11:02 AM
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
public class UploadFileException extends RuntimeException {
    String message;
    HttpStatus status;
    String[] params;

    public UploadFileException(String message) {
        super(message);
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
    }

    public UploadFileException(String message, String message1, HttpStatus status, String[] params) {
        super(message);
        this.message = message1;
        this.status = status;
        this.params = params;
    }

    public UploadFileException(String message, String[] params) {
        super(message);
        this.message = message;
        this.params = params;
        this.status = HttpStatus.BAD_REQUEST;
    }
}
