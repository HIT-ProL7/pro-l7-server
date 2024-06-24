package com.example.hitproduct.domain.mapper;
/*
 * @author HongAnh
 * @created 23 / 06 / 2024 - 4:08 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.domain.dto.request.CreateClassroomRequest;
import com.example.hitproduct.domain.dto.response.ClassroomResponse;
import com.example.hitproduct.domain.entity.Classroom;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {
    ClassroomMapper INSTANCE = Mappers.getMapper(ClassroomMapper.class);

    Classroom toClassroom(CreateClassroomRequest request);
    ClassroomResponse toClassroomResponse(Classroom classroom);
}
