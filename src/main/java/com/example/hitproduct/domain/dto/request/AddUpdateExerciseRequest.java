package com.example.hitproduct.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class AddUpdateExerciseRequest {
    private String title;
    private String content;
}
