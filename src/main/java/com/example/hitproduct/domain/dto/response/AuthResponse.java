package com.example.hitproduct.domain.dto.response;
/*
 * @author HongAnh
 * @created 14 / 06 / 2024 - 7:18 PM
 * @project HitProduct
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.domain.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AuthResponse {
    String accessToken;
    String refreshToken;
    String type;
    String roles;
    UserResponse   loggedInUser;

    public AuthResponse(String accessToken,String refreshToken,String type, String roles, UserResponse user) {
        this.accessToken = accessToken;
        this.type = "Bearer";
        this.roles = roles;
        this.refreshToken = refreshToken;
        this.loggedInUser = user;
    }
}
