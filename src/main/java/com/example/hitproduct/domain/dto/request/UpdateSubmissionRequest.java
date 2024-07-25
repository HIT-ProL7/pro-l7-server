package com.example.hitproduct.domain.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

/*
 * @author HongAnh
 * @created 25 / 07 / 2024 - 7:26 AM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateSubmissionRequest {
    String content;
}
