package com.example.hitproduct.domain.mapper;

import com.example.hitproduct.domain.dto.request.AddUpdateExerciseRequest;
import com.example.hitproduct.domain.dto.response.ExerciseResponse;
import com.example.hitproduct.domain.entity.Exercise;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {
    ExerciseMapper INSTANCE = Mappers.getMapper(ExerciseMapper.class);

    Exercise toExercise(AddUpdateExerciseRequest request);


    ExerciseResponse toExerciseResponse(Exercise exercise);
}
