package com.example.hitproduct.domain.dto.request;
/*
 * @author HongAnh
 * @created 25 / 06 / 2024 - 8:07 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.domain.entity.SeatRole;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddMemberRequest {
    String studentCode;
    Long classroomId;
    SeatRole seatRole;
}
