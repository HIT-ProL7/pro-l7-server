package com.example.hitproduct.service.impl;
/*
 * @author HongAnh
 * @created 13 / 07 / 2024 - 4:57 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.constant.ErrorMessage;
import com.example.hitproduct.constant.SeatRole;
import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.global.Status;
import com.example.hitproduct.domain.dto.request.CreateLessonRequest;
import com.example.hitproduct.domain.dto.response.LessonResponse;
import com.example.hitproduct.domain.entity.Classroom;
import com.example.hitproduct.domain.entity.Lesson;
import com.example.hitproduct.domain.entity.User;
import com.example.hitproduct.domain.mapper.LessonMapper;
import com.example.hitproduct.exception.NotFoundException;
import com.example.hitproduct.repository.ClassroomRepository;
import com.example.hitproduct.repository.LessonRepository;
import com.example.hitproduct.repository.UserRepository;
import com.example.hitproduct.service.LessonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    LessonRepository lessonRepository;
    UserRepository userRepository;
    ClassroomRepository classroomRepository;

    LessonMapper lessonMapper;

    @Override
    public GlobalResponse<Meta, LessonResponse> createLesson(CreateLessonRequest request, String studentCode) {
        User currentUser = userRepository.findByStudentCode(studentCode)
                .orElseThrow(()->new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND));

        Classroom classroom = classroomRepository.findById(request.getClassroomId())
                .orElseThrow(()->new NotFoundException(ErrorMessage.Classroom.ERR_NOTFOUND_BY_ID));

        boolean canModifyResource = isAdminOrLeader(currentUser, classroom);
        if(!canModifyResource){
            throw new AuthorizationServiceException(ErrorMessage.User.UNAUTHORIZED);
        }

        Lesson lesson = lessonMapper.toLesson(request);
        lesson.setClassroom(classroom);

        lesson = lessonRepository.save(lesson);

        return GlobalResponse
                .<Meta, LessonResponse>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(lessonMapper.toLessonResponse(lesson))
                .build();
    }

    @Override
    public GlobalResponse<Meta, List<LessonResponse>> getLessons(Integer id) {
        List<LessonResponse> list = lessonRepository.findAllByClassroomId(id).stream()
                .map(lesson -> lessonMapper.toLessonResponse(lesson))
                .collect(Collectors.toList());

        return GlobalResponse
                .<Meta, List<LessonResponse>>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(list)
                .build();
    }

    private boolean isAdminOrLeader(User currentUser, Classroom classroom){
        boolean isAdmin = currentUser.getRole().getName().contains("ADMIN");
        boolean isLeader = classroom.getPositions().stream()
                .anyMatch(position -> position.getUser().equals(currentUser)
                        && position.getSeatRole().equals(SeatRole.LEADER));

        if (!isAdmin && !isLeader) {
            return false;
        }
        return true;
    }
}
