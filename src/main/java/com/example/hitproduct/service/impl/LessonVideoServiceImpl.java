package com.example.hitproduct.service.impl;
/*
 * @author HongAnh
 * @created 13 / 07 / 2024 - 11:54 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.constant.ErrorMessage;
import com.example.hitproduct.constant.SeatRole;
import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.global.Status;
import com.example.hitproduct.domain.dto.request.CreateVideoRequest;
import com.example.hitproduct.domain.dto.response.VideoResponse;
import com.example.hitproduct.domain.entity.Classroom;
import com.example.hitproduct.domain.entity.Lesson;
import com.example.hitproduct.domain.entity.LessonVideo;
import com.example.hitproduct.domain.entity.User;
import com.example.hitproduct.domain.mapper.VideoMapper;
import com.example.hitproduct.exception.NotFoundException;
import com.example.hitproduct.repository.LessonRepository;
import com.example.hitproduct.repository.LessonVideoRepository;
import com.example.hitproduct.repository.UserRepository;
import com.example.hitproduct.service.LessonVideoService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class LessonVideoServiceImpl implements LessonVideoService {
    LessonVideoRepository videoRepository;
    LessonRepository lessonRepository;
    UserRepository userRepository;

    VideoMapper videoMapper;

    @Override
    public GlobalResponse<Meta, VideoResponse> createLessonVideo(CreateVideoRequest request, String studentCode) {
        User currentUser = userRepository.findByStudentCode(studentCode)
                                         .orElseThrow(()->new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND));

        Lesson lesson = lessonRepository.findById(request.getLessonId())
                                 .orElseThrow(()->new NotFoundException(ErrorMessage.Lesson.ERR_LESSON_NOT_FOUND));

        boolean canModifyResource = isAdminOrLeader(currentUser, lesson.getClassroom());
        if(!canModifyResource){
            throw new AuthorizationServiceException(ErrorMessage.User.UNAUTHORIZED);
        }

        LessonVideo video = videoMapper.toLessonVideo(request);
        video.setLesson(lesson);

        video = videoRepository.save(video);

        return GlobalResponse
                .<Meta, VideoResponse>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(videoMapper.toVideoResponse(video))
                .build();
    }

    private boolean isAdminOrLeader(User currentUser, Classroom classroom) {
        boolean isAdmin = currentUser.getRole().getName().contains("ADMIN");
        boolean isLeader = classroom.getPositions().stream()
                                    .anyMatch(position -> position.getUser().equals(currentUser)
                                                          && position.getSeatRole().equals(SeatRole.LEADER));

        if (!isAdmin && !isLeader){
            return false;
        }
        return true;
    }
}
