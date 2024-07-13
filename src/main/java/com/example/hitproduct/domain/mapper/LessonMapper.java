package com.example.hitproduct.domain.mapper;
/*
 * @author HongAnh
 * @created 13 / 07 / 2024 - 4:47 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.domain.dto.request.CreateLessonRequest;
import com.example.hitproduct.domain.dto.response.LessonResponse;
import com.example.hitproduct.domain.entity.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    LessonMapper INSTANCE = Mappers.getMapper(LessonMapper.class);

    Lesson toLesson(CreateLessonRequest request);

    LessonResponse toLessonResponse(Lesson lesson);
}
