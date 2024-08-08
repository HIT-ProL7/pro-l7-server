package com.example.hitproduct.domain.dto.response;
/*
 * @author HongAnh
 * @created 09 / 08 / 2024 - 1:00 AM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RefreshResponse {
    String accessToken;
    String refreshToken;
}
