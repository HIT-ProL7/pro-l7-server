package com.example.hitproduct.controller;

import com.example.hitproduct.constant.Endpoint;
import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.request.AddExerciseRequest;
import com.example.hitproduct.domain.dto.response.ExerciseResponse;
import com.example.hitproduct.domain.entity.User;
import com.example.hitproduct.service.ExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ExerciseController {
    private final ExerciseService exerciseService;

    @PostMapping(Endpoint.V1.Exercise.CREATE)
    public ResponseEntity<GlobalResponse<Meta, ExerciseResponse>> createExercise(
            @PathVariable Integer lessonId, @RequestBody AddExerciseRequest request, @AuthenticationPrincipal User currentUser
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(exerciseService.createExercise(lessonId, request, currentUser));
    }
}
