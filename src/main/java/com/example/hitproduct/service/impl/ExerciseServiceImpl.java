package com.example.hitproduct.service.impl;

import com.example.hitproduct.constant.ErrorMessage;
import com.example.hitproduct.constant.SeatRole;
import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.global.Status;
import com.example.hitproduct.domain.dto.request.AddUpdateExerciseRequest;
import com.example.hitproduct.domain.dto.response.ExerciseResponse;
import com.example.hitproduct.domain.entity.Classroom;
import com.example.hitproduct.domain.entity.Exercise;
import com.example.hitproduct.domain.entity.Lesson;
import com.example.hitproduct.domain.entity.User;
import com.example.hitproduct.domain.mapper.ExerciseMapper;
import com.example.hitproduct.exception.ForbiddenException;
import com.example.hitproduct.exception.NotFoundException;
import com.example.hitproduct.repository.ExerciseRepository;
import com.example.hitproduct.repository.LessonRepository;
import com.example.hitproduct.service.ExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final LessonRepository   lessonRepository;

    @Override
    public GlobalResponse<Meta, ExerciseResponse> createExercise(Integer lessonId, AddUpdateExerciseRequest request, User currentUser) {
        Lesson lesson = lessonRepository.findById(lessonId)
                                        .orElseThrow(() -> new NotFoundException(ErrorMessage.Lesson.ERR_LESSON_NOT_FOUND));

        if (!isAdminOrLeader(currentUser, lesson.getClassroom())) {
            throw new ForbiddenException(ErrorMessage.Classroom.ERR_FORBIDDEN);
        }
        Exercise mappedExercise = ExerciseMapper.INSTANCE.toExercise(request);
        mappedExercise.setLesson(lesson);
        Exercise savedExercise = exerciseRepository.save(mappedExercise);

        return GlobalResponse
                .<Meta, ExerciseResponse>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(ExerciseMapper.INSTANCE.toExerciseResponse(savedExercise))
                .build();
    }

    @Override
    public GlobalResponse<Meta, ExerciseResponse> updateExercise(Integer lessonId, Integer exerciseId, AddUpdateExerciseRequest request, User currentUser) {
        Lesson lesson = lessonRepository.findById(lessonId)
                                        .orElseThrow(() -> new NotFoundException(ErrorMessage.Lesson.ERR_LESSON_NOT_FOUND));

        if (!isAdminOrLeader(currentUser, lesson.getClassroom())) {
            throw new ForbiddenException(ErrorMessage.Classroom.ERR_FORBIDDEN);
        }
        Optional<Exercise> foundExercise = lesson.getExercises().stream().filter(exercise -> exercise.getId().equals(exerciseId)).findFirst();
        if (foundExercise.isEmpty()) {
            throw new NotFoundException(ErrorMessage.Exercise.ERR_EXERCISE_NOT_FOUND);
        }
        Exercise exercise = foundExercise.get();
        if (request.getTitle() != null && !request.getTitle().isBlank()) {
            exercise.setTitle(request.getTitle());
        }
        if (request.getContent() != null && !request.getContent().isBlank()) {
            exercise.setContent(request.getContent());
        }
        Exercise savedExercise = exerciseRepository.save(exercise);

        return GlobalResponse
                .<Meta, ExerciseResponse>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(ExerciseMapper.INSTANCE.toExerciseResponse(savedExercise))
                .build();
    }

    private boolean isAdminOrLeader(User currentUser, Classroom classroom) {
        boolean isAdmin = currentUser.getRole().getName().contains("ADMIN");
        boolean isLeader = classroom.getPositions().stream()
                                    .anyMatch(position -> position.getUser().equals(currentUser)
                                                          && position.getSeatRole().equals(SeatRole.LEADER));

        return isAdmin || isLeader;
    }
}
