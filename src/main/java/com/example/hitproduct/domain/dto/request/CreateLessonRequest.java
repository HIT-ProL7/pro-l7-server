package com.example.hitproduct.domain.dto.request;
/*
 * @author HongAnh
 * @created 13 / 07 / 2024 - 4:46 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.constant.ValidationMessage;
import com.example.hitproduct.domain.entity.LessonVideo;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateLessonRequest {
    @NotEmpty(message = ValidationMessage.Lesson.LESSON_NAME_NO_EMPTY)
    String name;

    String title;

    String content;
    LessonVideo video;

    @NotEmpty(message = ValidationMessage.Lesson.CLASSROOM_ID_NOT_EMPTY)
    Integer classroomId;
}
