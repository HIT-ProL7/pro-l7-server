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
import com.example.hitproduct.domain.dto.request.AddMemberRequest;
import com.example.hitproduct.domain.dto.request.CreateClassroomRequest;
import com.example.hitproduct.domain.dto.response.ClassroomResponse;
import com.example.hitproduct.domain.entity.Classroom;
import com.example.hitproduct.domain.entity.Position;
import com.example.hitproduct.domain.entity.SeatRole;
import com.example.hitproduct.domain.entity.User;
import com.example.hitproduct.domain.mapper.ClassroomMapper;
import com.example.hitproduct.exception.AlreadyExistsException;
import com.example.hitproduct.exception.NotFoundException;
import com.example.hitproduct.repository.ClassroomRepository;
import com.example.hitproduct.repository.PositionRepository;
import com.example.hitproduct.repository.UserRepository;
import com.example.hitproduct.service.ClassroomService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassroomServiceImpl implements ClassroomService {

    ClassroomRepository classroomRepository;
    ClassroomMapper classroomMapper;
    UserRepository userRepository;
    PositionRepository positionRepository;

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
    @Transactional
    public GlobalResponse<Meta, String> addMember(Long classroomId, AddMemberRequest request, String studentCode) {
        Optional<Classroom> classroomOptional = classroomRepository.findById(classroomId);
        if (classroomOptional.isEmpty()) {
            throw new NotFoundException(ErrorMessage.Classroom.ERR_NOTFOUND_BY_ID);
        }

        Classroom classroom = classroomOptional.get();

        Optional<User> userOptional = userRepository.findByStudentCode(studentCode);

        if (userOptional.isEmpty()) {
            throw new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND);
        }

        User currentUser = userOptional.get();

        boolean isLeader = classroom.getPositions().stream()
                .anyMatch(position -> position.getUser().equals(currentUser)
                        && position.getSeatRole().equals(SeatRole.LEADER));

        boolean isAdmin = currentUser.getRole().getId().equals(1L);

        if (!isAdmin && !isLeader) {
            throw new AuthorizationServiceException(ErrorMessage.User.UNAUTHORIZED);
        }

        Optional<User> newUserOpt = userRepository.findByStudentCode(request.getStudentCode());
        if (newUserOpt.isEmpty()) {
            throw new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND);
        }

        User newUser = newUserOpt.get();

        Position position = null;
        if (request.getSeatRole() != null) {
            position = Position.builder()
                    .user(newUser)
                    .classroom(classroom)
                    .seatRole(request.getSeatRole())
                    .build();
        } else {
            position = Position.builder()
                    .user(newUser)
                    .classroom(classroom)
                    .seatRole(SeatRole.MEMBER)
                    .build();
        }

        positionRepository.save(position);
        return GlobalResponse
                .<Meta, String>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data("Member added successfully!")
                .build();
    }
}
