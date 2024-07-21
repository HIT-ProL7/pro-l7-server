package com.example.hitproduct.domain.dto.response;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExerciseResponse {
    private Integer id;
    private String  title;
    private String  content;
    private Date    createdAt;
    private Date    updatedAt;
}
