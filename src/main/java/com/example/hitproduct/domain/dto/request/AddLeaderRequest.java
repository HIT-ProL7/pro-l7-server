package com.example.hitproduct.domain.dto.request;
/*
 * @author HongAnh
 * @created 25 / 06 / 2024 - 9:02 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddLeaderRequest {
    Long classroomId;
    String studentCode;
}
