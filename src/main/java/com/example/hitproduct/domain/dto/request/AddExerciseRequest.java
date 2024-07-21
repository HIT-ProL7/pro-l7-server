package com.example.hitproduct.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class AddExerciseRequest {
    private String title;
    private String content;
}
