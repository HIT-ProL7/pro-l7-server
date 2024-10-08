package com.example.hitproduct.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ForbiddenException extends RuntimeException {
    private String message;
}
