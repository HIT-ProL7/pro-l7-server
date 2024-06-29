package com.example.hitproduct.service.impl;
/*
 * @author HongAnh
 * @created 23 / 06 / 2024 - 4:14 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.constant.ErrorMessage;
import com.example.hitproduct.constant.SuccessMessage;
import com.example.hitproduct.domain.dto.global.BlankData;
import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.global.Status;
import com.example.hitproduct.domain.dto.request.CreateClassroomRequest;
import com.example.hitproduct.domain.dto.response.ClassroomResponse;
import com.example.hitproduct.domain.entity.Classroom;
import com.example.hitproduct.domain.entity.SeatRole;
import com.example.hitproduct.domain.mapper.ClassroomMapper;
import com.example.hitproduct.exception.AlreadyExistsException;
import com.example.hitproduct.exception.ForbiddenException;
import com.example.hitproduct.exception.NotFoundException;
import com.example.hitproduct.repository.ClassroomRepository;
import com.example.hitproduct.service.ClassroomService;
import com.example.hitproduct.util.MessageSourceUtil;
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
    private final MessageSourceUtil messageSourceUtil;

    @Override
    public GlobalResponse<Meta, ClassroomResponse> createClass(CreateClassroomRequest request) {

        if (classroomRepository.existsByName(request.getName())) {
            throw new AlreadyExistsException(ErrorMessage.Classroom.ERR_EXISTS_CLASSNAME);
        }
        Classroom classroom = classroomMapper.toClassroom(request);
        classroom.setClosed(false);

        classroom = classroomRepository.save(classroom);

        return GlobalResponse
                .<Meta, ClassroomResponse>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(classroomMapper.toClassroomResponse(classroom))
                .build();
    }

    @Override
    public GlobalResponse<Meta, Void> closeClassroom(String userId, Integer classroomId) {
        Classroom foundClassroom = classroomRepository
                .findById(classroomId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Classroom.ERR_NOT_FOUND));

        foundClassroom.getPositions()
                      .parallelStream().filter(item ->
                              item.getSeatRole().equals(SeatRole.LEADER)
                              && item.getUser().getId().equals(userId)
                      )
                      .findFirst()
                      .orElseThrow(() -> new ForbiddenException(ErrorMessage.Classroom.ERR_FORBIDDEN));

        foundClassroom.setClosed(true);
        classroomRepository.save(foundClassroom);

        return GlobalResponse
                .<Meta, Void>builder()
                .meta(Meta.builder()
                          .status(Status.SUCCESS)
                          .message(messageSourceUtil.getLocalizedMessage(SuccessMessage.Classroom.CLOSED))
                          .build()
                )
                .build();
    }
}
