package com.example.hitproduct.domain.dto.response;
/*
 * @author HongAnh
 * @created 22 / 07 / 2024 - 5:47 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubmissionResponse {
    Integer id;
    String content;
    Timestamp createAt;
    Timestamp updatedAt;
    UserResponse createdBy;
    Boolean editable;
}
