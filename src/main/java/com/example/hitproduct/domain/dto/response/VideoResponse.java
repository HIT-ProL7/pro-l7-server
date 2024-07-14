package com.example.hitproduct.domain.dto.response;
/*
 * @author HongAnh
 * @created 14 / 07 / 2024 - 12:20 AM
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
public class VideoResponse {
    Integer id;
    String title;
    String description;
    String url;
}
