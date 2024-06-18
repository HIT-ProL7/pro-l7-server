package com.example.hitproduct.service;
/*
 * @author HongAnh
 * @created 14 / 06 / 2024 - 6:09 PM
 * @project HitProduct
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.request.ChangePasswordRequest;
import com.example.hitproduct.domain.dto.request.UpdateInfoRequest;
import com.example.hitproduct.domain.dto.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    GlobalResponse<Meta, UserResponse> getUserByStudentCode(String studentCode);

    GlobalResponse<Meta, UserResponse> updateUser(String studentCode, UpdateInfoRequest request);

    GlobalResponse<Meta, UserResponse> changePassword(String studentCode, ChangePasswordRequest request);
}
