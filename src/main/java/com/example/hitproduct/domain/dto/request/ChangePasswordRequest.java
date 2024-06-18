package com.example.hitproduct.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(
        @NotBlank(message = "Old password field is required")
        String oldPassword,

        @NotBlank(message = "New password field is required")
        String newPassword,

        @NotBlank(message = "Confirm new password field is required")
        String confirmNewPassword
) {
}
