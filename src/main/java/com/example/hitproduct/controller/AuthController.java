package com.example.hitproduct.controller;
/*
 * @author HongAnh
 * @created 14 / 06 / 2024 - 6:54 PM
 * @project HitProduct
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.constant.Endpoint;
import com.example.hitproduct.domain.dto.global.BlankData;
import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.request.AddUserRequest;
import com.example.hitproduct.domain.dto.request.LoginRequest;
import com.example.hitproduct.domain.dto.request.UpdateInfoRequest;
import com.example.hitproduct.domain.dto.response.AuthResponse;
import com.example.hitproduct.domain.dto.response.UserResponse;
import com.example.hitproduct.domain.mapper.UserMapper;
import com.example.hitproduct.security.jwt.JwtUtils;
import com.example.hitproduct.service.AuthService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthService           authService;
    UserMapper            userMapper;
    AuthenticationManager authenticationManager;
    JwtUtils              jwtUtils;

    @PostMapping(Endpoint.V1.Auth.REGISTER)
    public ResponseEntity<GlobalResponse<Meta, UserResponse>> registerUser(
            @RequestBody @Valid AddUserRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(request));
    }

    @PostMapping(Endpoint.V1.Auth.LOGIN)
    public ResponseEntity<GlobalResponse<Meta, AuthResponse>> login(
            @Valid @RequestBody LoginRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.login(request));
    }

    @PatchMapping(Endpoint.V1.Auth.FORGOT_PASSWORD)
    public ResponseEntity<GlobalResponse<Meta, BlankData>> forgotPassword(
            @PathVariable String studentCode
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.forgetPassword(studentCode));
    }
}
