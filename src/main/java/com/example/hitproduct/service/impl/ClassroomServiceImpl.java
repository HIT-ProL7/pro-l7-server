package com.example.hitproduct.service.impl;
/*
 * @author HongAnh
 * @created 23 / 06 / 2024 - 4:14 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.constant.ErrorMessage;
import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.global.Status;
import com.example.hitproduct.domain.dto.request.CreateClassroomRequest;
import com.example.hitproduct.domain.dto.request.EditClassroomRequest;
import com.example.hitproduct.domain.dto.response.ClassroomResponse;
import com.example.hitproduct.domain.entity.Classroom;
import com.example.hitproduct.domain.entity.SeatRole;
import com.example.hitproduct.domain.entity.User;
import com.example.hitproduct.domain.mapper.ClassroomMapper;
import com.example.hitproduct.exception.AlreadyExistsException;
import com.example.hitproduct.exception.ForbiddenException;
import com.example.hitproduct.exception.NotFoundException;
import com.example.hitproduct.repository.ClassroomRepository;
import com.example.hitproduct.service.ClassroomService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassroomServiceImpl implements ClassroomService {

    ClassroomRepository classroomRepository;
    ClassroomMapper     classroomMapper;

    @Override
    public GlobalResponse<Meta, ClassroomResponse> createClass(CreateClassroomRequest request) {

        if (classroomRepository.existsByName(request.getName())) {
            throw new AlreadyExistsException(ErrorMessage.Classroom.ERR_EXISTS_CLASSNAME);
        }
        Classroom classroom = classroomMapper.toClassroom(request);
        classroom.setStatus(true);

        classroom = classroomRepository.save(classroom);

        return GlobalResponse
                .<Meta, ClassroomResponse>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(classroomMapper.toClassroomResponse(classroom))
                .build();
    }

    @Override
    public GlobalResponse<Meta, ClassroomResponse> editClassroom(User currentUser, Integer classroomId, EditClassroomRequest request) {
        Classroom foundClassroom = classroomRepository
                .findById(classroomId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Classroom.ERR_NOT_FOUND));

        boolean canUpdate = foundClassroom
                .getPositions()
                .parallelStream()
                .anyMatch(item -> item.getSeatRole().equals(SeatRole.LEADER) && item.getUser().getId().equals(currentUser.getId()));

        if (!canUpdate && !currentUser.getRole().getName().equals("ROLE_ADMIN")) {
            throw new ForbiddenException(ErrorMessage.Classroom.ERR_FORBIDDEN);
        }

        if (request.getName() != null) foundClassroom.setName(request.getName());
        if (request.getDescription() != null) foundClassroom.setDescription(request.getDescription());
        if (request.getRoadmap() != null) foundClassroom.setRoadmap(request.getRoadmap());

        Classroom updatedClassroom = classroomRepository.save(foundClassroom);

        return GlobalResponse
                .<Meta, ClassroomResponse>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(classroomMapper.toClassroomResponse(updatedClassroom))
                .build();
    }
}
