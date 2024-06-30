package com.example.hitproduct.domain.dto.response;
/*
 * @author HongAnh
 * @created 30 / 06 / 2024 - 10:15 AM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetClassroomResponse {
    String name;
    String description;
    String roadmap;
    Timestamp createAt;
    Timestamp startedDate;
    List<UserResponse> leaders;
}
