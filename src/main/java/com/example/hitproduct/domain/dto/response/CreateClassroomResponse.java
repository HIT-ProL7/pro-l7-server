package com.example.hitproduct.domain.dto.response;
/*
 * @author HongAnh
 * @created 23 / 06 / 2024 - 4:12 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.domain.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateClassroomResponse {
    Integer id;
    String name;
    String logo;
    String description;
    String roadmap;
    Timestamp      createAt;
    OffsetDateTime startedDate;
}
