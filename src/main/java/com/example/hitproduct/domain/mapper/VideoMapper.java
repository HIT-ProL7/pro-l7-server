package com.example.hitproduct.domain.mapper;
/*
 * @author HongAnh
 * @created 14 / 07 / 2024 - 12:17 AM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.domain.dto.request.LessonVideoRequest;
import com.example.hitproduct.domain.dto.response.VideoResponse;
import com.example.hitproduct.domain.entity.LessonVideo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface VideoMapper {
    VideoMapper INSTANCE = Mappers.getMapper(VideoMapper.class);

    LessonVideo toLessonVideo(LessonVideoRequest request);

    VideoResponse toVideoResponse(LessonVideo lessonVideo);

    void updateLessonVideoFromDto(LessonVideoRequest request, @MappingTarget LessonVideo lessonVideo);
}
