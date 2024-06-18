package com.example.hitproduct.domain.dto.request;
/*
 * @author HongAnh
 * @created 14 / 06 / 2024 - 6:10 PM
 * @project HitProduct
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.constant.ValidationMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    @Size(min = 10, message = ValidationMessage.User.STUDENT_CODE_INVALID)
    @Pattern(regexp = "\\d+", message = ValidationMessage.User.STUDENT_CODE_NUMERIC)
    String studentCode;

    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = ValidationMessage.User.PASSWORD_INVALID)
    String password;

    @NotEmpty(message = "Email is not empty")
    @Email
    String email;

    @NotEmpty(message = "Fullname is not empty")
    String fullName;
}
