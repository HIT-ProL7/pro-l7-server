package com.example.hitproduct.domain.dto.response;
/*
 * @author HongAnh
 * @created 14 / 06 / 2024 - 6:12 PM
 * @project HitProduct
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
public class UserResponse {
    String    id;
    String    studentCode;
    String    fullName;
    String    email;
    Timestamp createdAt;
    Timestamp updatedAt;
    Integer   cohort;
    String    description;
}
