package com.example.hitproduct.domain.dto.response;
/*
 * @author HongAnh
 * @created 14 / 06 / 2024 - 7:18 PM
 * @project HitProduct
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtResponse {
    String id;
    String email;
    String jwt;
    String type = "Bearer";
    String roles;

    public JwtResponse(String id, String email, String jwt, String roles) {
        this.id = id;
        this.email = email;
        this.jwt = jwt;
        this.roles = roles;
    }
}
