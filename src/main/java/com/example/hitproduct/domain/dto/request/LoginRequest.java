package com.example.hitproduct.domain.dto.request;
/*
 * @author HongAnh
 * @created 14 / 06 / 2024 - 7:16 PM
 * @project HitProduct
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.constant.ValidationMessage;
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
public class LoginRequest {
    @NotEmpty(message = "This field cannot empty")
    @Size(min = 10, message = ValidationMessage.Login.STUDENT_CODE_INVALID)
    String studentCode;

    @NotEmpty(message = "This field cannot empty")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = ValidationMessage.Login.PASSWORD_INVALID)
    String password;
}
