package com.example.hitproduct.domain.mapper;

import com.example.hitproduct.domain.dto.request.AddExerciseRequest;
import com.example.hitproduct.domain.dto.response.ExerciseResponse;
import com.example.hitproduct.domain.entity.Exercise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {
    ExerciseMapper INSTANCE = Mappers.getMapper(ExerciseMapper.class);
    
    Exercise toExercise(AddExerciseRequest request);

    ExerciseResponse toExerciseResponse(Exercise exercise);
}
