package com.example.hitproduct.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InternalServerErrorException extends RuntimeException {
    private String message;
}
