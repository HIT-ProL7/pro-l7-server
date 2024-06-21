package com.example.hitproduct.service;
/*
 * @author HongAnh
 * @created 14 / 06 / 2024 - 6:20 PM
 * @project HitProduct
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.domain.dto.global.BlankData;
import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.request.LoginRequest;
import com.example.hitproduct.domain.dto.request.UpdateInfoRequest;
import com.example.hitproduct.domain.dto.request.AddUserRequest;
import com.example.hitproduct.domain.dto.response.AuthResponse;
import com.example.hitproduct.domain.dto.response.UserResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {

    GlobalResponse<Meta, UserResponse> register(AddUserRequest userRequest);

    GlobalResponse<Meta, AuthResponse> login(LoginRequest loginRequest);

    GlobalResponse<Meta, BlankData> forgetPassword(String studentCode);

}
