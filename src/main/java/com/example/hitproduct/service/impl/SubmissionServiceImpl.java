package com.example.hitproduct.service.impl;
/*
 * @author HongAnh
 * @created 22 / 07 / 2024 - 5:34 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.constant.CommonConstant;
import com.example.hitproduct.constant.ErrorMessage;
import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.global.Status;
import com.example.hitproduct.domain.dto.request.SubmissionRequest;
import com.example.hitproduct.domain.dto.response.SubmissionResponse;
import com.example.hitproduct.domain.entity.Exercise;
import com.example.hitproduct.domain.entity.Submission;
import com.example.hitproduct.domain.entity.User;
import com.example.hitproduct.domain.mapper.SubmissionMapper;
import com.example.hitproduct.domain.mapper.UserMapper;
import com.example.hitproduct.exception.ForbiddenException;
import com.example.hitproduct.repository.ExerciseRepository;
import com.example.hitproduct.repository.SubmissionRepository;
import com.example.hitproduct.repository.UserRepository;
import com.example.hitproduct.service.SubmissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {
    SubmissionRepository submissionRepository;
    ExerciseRepository exerciseRepository;
    UserRepository userRepository;

    SubmissionMapper submissionMapper;
    UserMapper userMapper;

    @Transactional
    @Override
    public GlobalResponse<Meta, SubmissionResponse> createSubmission(SubmissionRequest request, String studentCode) {
        Submission submission = submissionMapper.toSubmission(request);
        User currentUser = userRepository.findByStudentCode(studentCode).get();
        Exercise exercise = exerciseRepository.findById(request.getExerciseId()).get();

        submission.setUser(currentUser);
        submission.setExercise(exercise);

        submission = submissionRepository.save(submission);

        SubmissionResponse response = submissionMapper.toSubmissionResponse(submission);
        response.setEditable(CommonConstant.Submission.CAN_EDIT);
        response.setCreatedBy(userMapper.toUserResponse(currentUser));

        return GlobalResponse
                .<Meta, SubmissionResponse>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(response)
                .build();
    }
}
