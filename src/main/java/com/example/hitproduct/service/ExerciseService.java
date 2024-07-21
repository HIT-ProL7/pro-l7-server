package com.example.hitproduct.service;

import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.request.AddUpdateExerciseRequest;
import com.example.hitproduct.domain.dto.response.ExerciseResponse;
import com.example.hitproduct.domain.entity.User;

public interface ExerciseService {
    GlobalResponse<Meta, ExerciseResponse> createExercise(Integer lessonId, AddUpdateExerciseRequest request, User currentUser);

    GlobalResponse<Meta, ExerciseResponse> updateExercise(
            Integer lessonId, Integer exerciseId, AddUpdateExerciseRequest request, User currentUser
    );

    GlobalResponse<Meta, Void> deleteExercise(Integer lessonId, Integer exerciseId, User currentUser);
}
