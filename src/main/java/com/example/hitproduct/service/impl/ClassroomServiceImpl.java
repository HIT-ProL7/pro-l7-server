package com.example.hitproduct.service.impl;
/*
 * @author HongAnh
 * @created 23 / 06 / 2024 - 4:14 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.constant.CommonConstant;
import com.example.hitproduct.constant.ErrorMessage;
import com.example.hitproduct.constant.SuccessMessage;
import com.example.hitproduct.domain.dto.global.BlankData;
import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.global.Status;
import com.example.hitproduct.domain.dto.request.AddMemberRequest;
import com.example.hitproduct.domain.dto.request.CreateClassroomRequest;
import com.example.hitproduct.domain.dto.request.EditClassroomRequest;
import com.example.hitproduct.domain.dto.response.CreateClassroomResponse;
import com.example.hitproduct.domain.dto.response.GetClassroomResponse;
import com.example.hitproduct.domain.dto.response.UserResponse;
import com.example.hitproduct.domain.entity.Classroom;
import com.example.hitproduct.domain.entity.Position;
import com.example.hitproduct.constant.SeatRole;
import com.example.hitproduct.domain.entity.User;
import com.example.hitproduct.domain.mapper.ClassroomMapper;
import com.example.hitproduct.domain.mapper.UserMapper;
import com.example.hitproduct.exception.AlreadyExistsException;
import com.example.hitproduct.exception.ForbiddenException;
import com.example.hitproduct.exception.NotFoundException;
import com.example.hitproduct.repository.ClassroomRepository;
import com.example.hitproduct.repository.PositionRepository;
import com.example.hitproduct.repository.UserRepository;
import com.example.hitproduct.service.ClassroomService;
import com.example.hitproduct.util.MessageSourceUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassroomServiceImpl implements ClassroomService {

    ClassroomRepository classroomRepository;
    UserRepository      userRepository;
    PositionRepository  positionRepository;

    ClassroomMapper   classroomMapper;
    UserMapper        userMapper;
    MessageSourceUtil messageSourceUtil;

    @Override
    public GlobalResponse<Meta, CreateClassroomResponse> createClass(CreateClassroomRequest request) {

        if (classroomRepository.existsByName(request.getName())) {
            throw new AlreadyExistsException(ErrorMessage.Classroom.ERR_EXISTS_CLASSNAME);
        }
        Classroom classroom = classroomMapper.toClassroom(request);
        classroom.setClosed(false);

        classroom = classroomRepository.save(classroom);

        return GlobalResponse
                .<Meta, CreateClassroomResponse>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(classroomMapper.toClassroomResponse(classroom))
                .build();
    }

    @Override
    public GlobalResponse<Meta, Void> closeClassroom(User currentUser, Integer classroomId) {
        Classroom foundClassroom = classroomRepository
                .findById(classroomId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Classroom.ERR_NOT_FOUND));

        boolean canClose = foundClassroom
                .getPositions()
                .parallelStream().anyMatch(item -> item.getSeatRole().equals(SeatRole.LEADER) &&
                                                   item.getUser().getId().equals(currentUser.getId())
                );

        if (!canClose && !currentUser.getRole().getName().equals("ROLE_ADMIN")) {
            throw new ForbiddenException(ErrorMessage.Classroom.ERR_FORBIDDEN);
        }

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

    public GlobalResponse<Meta, GetClassroomResponse> editClassroom(User currentUser, Integer classroomId, EditClassroomRequest request) {
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
                .<Meta, GetClassroomResponse>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(classroomMapper.toGetClassroomResponse(updatedClassroom))
                .build();
    }

    public GlobalResponse<Meta, GetClassroomResponse> getMembersOfClassroom(
            User currentUser,
            Integer classroomId
    ) {
        Classroom classroom = classroomRepository.findById(classroomId).orElseThrow(() ->
                new NotFoundException(ErrorMessage.Classroom.ERR_NOT_FOUND)
        );

        boolean canGo = false;
        if (currentUser.getRole().getName().equals("ROLE_ADMIN")) {
            canGo = true;
        }

        if (!canGo) {
            classroom.getPositions()
                     .parallelStream()
                     .filter(item -> item.getUser().getId().equals(currentUser.getId()) && item.getSeatRole().equals(SeatRole.LEADER))
                     .findFirst()
                     .orElseThrow(() -> new ForbiddenException(ErrorMessage.Classroom.ERR_FORBIDDEN));
        }

        return GlobalResponse
                .<Meta, GetClassroomResponse>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(GetClassroomResponse
                        .builder()
                        .name(classroom.getName())
                        .description(classroom.getDescription())
                        .roadmap(classroom.getRoadmap())
                        .createAt(classroom.getCreateAt())
                        .members(classroom.getPositions().parallelStream().map(Position::getUser).toList())
                        .build()
                )
                .build();
    }

    @Transactional
    public GlobalResponse<Meta, String> addMember(Integer classroomId, AddMemberRequest request, String studentCode) {
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

        boolean canModifyResource = isAdminOrLeader(currentUser, classroom);
        if (!canModifyResource) {
            throw new ForbiddenException(ErrorMessage.User.UNAUTHORIZED);
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

    @Override
    public GlobalResponse<Meta, GetClassroomResponse> getClassroom(Integer id) {
        Optional<Classroom> classroomOptional = classroomRepository.findById(id);
        if (classroomOptional.isEmpty()) {
            throw new NotFoundException(ErrorMessage.Classroom.ERR_NOTFOUND_BY_ID);
        }

        GetClassroomResponse classroomResponse = getClassroomResponse(classroomOptional.get());
        return GlobalResponse
                .<Meta, GetClassroomResponse>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(classroomResponse)
                .build();
    }

    @Override
    public GlobalResponse<Meta, List<GetClassroomResponse>> getClassrooms() {
        List<GetClassroomResponse> responses = new ArrayList<>();
        List<Classroom> classrooms = classroomRepository.findAllByClosed(CommonConstant.Classroom.IS_CLOSE);
        for (Classroom classroom : classrooms) {
            GetClassroomResponse classroomResponse = getClassroomResponse(classroom);
            responses.add(classroomResponse);
        }

        return GlobalResponse
                .<Meta, List<GetClassroomResponse>>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(responses)
                .build();
    }

    @Override
    @Transactional
    public GlobalResponse<Meta, String> deleteMember(String username, Integer classId, String userId) {
        User currentUser = userRepository.findByStudentCode(username)
                                         .orElseThrow(() -> new UsernameNotFoundException(ErrorMessage.User.ERR_NOT_FOUND));

        Classroom classroom = classroomRepository.findById(classId)
                                                 .orElseThrow(() -> new NotFoundException(ErrorMessage.Classroom.ERR_NOTFOUND_BY_ID));

        boolean canModifyResource = isAdminOrLeader(currentUser, classroom);
        if (!canModifyResource) {
            throw new ForbiddenException(ErrorMessage.User.UNAUTHORIZED);
        }

        Position position = positionRepository.findByUserIdAndClassroomId(userId, classId)
                                              .orElseThrow(() -> new NotFoundException(ErrorMessage.User.NOT_FOUND_IN_CLASS));

        positionRepository.deleteByPositionId(position.getId());

        return GlobalResponse
                .<Meta, String>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data("Delete member from classroom successfully!")
                .build();
    }

    @Override
    public GlobalResponse<Meta, String> editMemberRole(String username, Integer classId, String userId, String role) {
        User currentUser = userRepository.findByStudentCode(username)
                                         .orElseThrow(() -> new UsernameNotFoundException(ErrorMessage.User.ERR_NOT_FOUND));

        Classroom classroom = classroomRepository.findById(classId)
                                                 .orElseThrow(() -> new NotFoundException(ErrorMessage.Classroom.ERR_NOTFOUND_BY_ID));

        boolean canModifyResource = isAdminOrLeader(currentUser, classroom);
        if (!canModifyResource) {
            throw new ForbiddenException(ErrorMessage.User.UNAUTHORIZED);
        }

        Position position = positionRepository.findByUserIdAndClassroomId(userId, classId)
                                              .orElseThrow(() -> new NotFoundException(ErrorMessage.User.NOT_FOUND_IN_CLASS));

        SeatRole newRole;
        try {
            newRole = SeatRole.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }

        position.setSeatRole(newRole);
        positionRepository.save(position);

        return GlobalResponse
                .<Meta, String>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data("Updated member role in classroom successfully!")
                .build();
    }

    @Override
    public GlobalResponse<Meta, List<GetClassroomResponse>> getMyClassroom(String studentCode) {
        List<GetClassroomResponse> responses = new ArrayList<>();
        Optional<User> userOptional = userRepository.findByStudentCode(studentCode);
        if (userOptional.isEmpty()) {
            throw new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND);
        }

        List<Classroom> classrooms = positionRepository
                .findActiveClassroomsByUserId(userOptional.get().getId(),
                        CommonConstant.Classroom.IS_OPEN);

        for (Classroom classroom : classrooms) {
            GetClassroomResponse classroomResponse = getClassroomResponse(classroom);
            responses.add(classroomResponse);
        }

        return GlobalResponse
                .<Meta, List<GetClassroomResponse>>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(responses)
                .build();
    }

    private boolean isAdminOrLeader(User currentUser, Classroom classroom) {
        boolean isAdmin = currentUser.getRole().getName().contains("ADMIN");
        boolean isLeader = classroom.getPositions().stream()
                                    .anyMatch(position -> position.getUser().equals(currentUser)
                                                          && position.getSeatRole().equals(SeatRole.LEADER));

        if (!isAdmin && !isLeader) {
            return false;
        }
        return true;
    }


    private GetClassroomResponse getClassroomResponse(Classroom classroom) {
        GetClassroomResponse classroomResponse = classroomMapper.toGetClassroomResponse(classroom);

        List<Position> positions = positionRepository.findAllByClassroom(classroom);

        List<UserResponse> userResponses = positions.stream()
                                                    .filter(position -> position.getSeatRole().equals(SeatRole.LEADER))
                                                    .map(position -> position.getUser().getId())
                                                    .map(userRepository::findById)
                                                    .filter(Optional::isPresent)
                                                    .map(Optional::get)
                                                    .map(userMapper::toUserResponse)
                                                    .collect(Collectors.toList());

        classroomResponse.setLeaders(userResponses);
        return classroomResponse;
    }
}
