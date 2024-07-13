package com.example.hitproduct.domain.dto.request;
/*
 * @author HongAnh
 * @created 14 / 06 / 2024 - 9:02 PM
 * @project HitProduct
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public record UpdateInfoRequest(
        @NotEmpty(message = "Email is not empty")
        String email,

        @NotEmpty(message = "Username is not empty")
        String fullName,

        // khoá
        Integer cohort,

        MultipartFile avatar,
        MultipartFile banner,

        String githubUrl,
        String facebookUrl,

        @Length(max = 255, message = "Description must be up to 255 characters.")
        String description
) {
}
