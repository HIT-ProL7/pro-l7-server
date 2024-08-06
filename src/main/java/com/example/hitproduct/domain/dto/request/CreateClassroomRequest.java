package com.example.hitproduct.domain.dto.request;
/*
 * @author HongAnh
 * @created 23 / 06 / 2024 - 4:09 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.constant.ValidationMessage;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateClassroomRequest {
    @NotEmpty(message = ValidationMessage.Classroom.CLASSNAME_NOT_BLANK)
    String name;
    MultipartFile logoImg;
    String description;
    String roadmap;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    OffsetDateTime startedDate;
}
