package com.example.hitproduct.service;

import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.request.AddExerciseRequest;
import com.example.hitproduct.domain.dto.response.ExerciseResponse;
import com.example.hitproduct.domain.entity.User;

public interface ExerciseService {
    GlobalResponse<Meta, ExerciseResponse> createExercise(Integer lessonId, AddExerciseRequest request, User currentUser);
}
