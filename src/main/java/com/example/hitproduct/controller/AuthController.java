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
import com.example.hitproduct.domain.dto.request.*;
import com.example.hitproduct.domain.dto.response.AuthResponse;
import com.example.hitproduct.domain.dto.response.RefreshResponse;
import com.example.hitproduct.domain.dto.response.UserResponse;
import com.example.hitproduct.domain.mapper.UserMapper;
import com.example.hitproduct.security.jwt.JwtUtils;
import com.example.hitproduct.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Register User", description = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping(Endpoint.V1.Auth.REGISTER)
    public ResponseEntity<GlobalResponse<Meta, UserResponse>> registerUser(
            @RequestBody @Valid AddUserRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(request));
    }

    @Operation(summary = "User Login", description = "Authenticate user and generate JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    @PostMapping(Endpoint.V1.Auth.LOGIN)
    public ResponseEntity<GlobalResponse<Meta, AuthResponse>> login(
            @Valid @RequestBody LoginRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.login(request));
    }

    @PostMapping(Endpoint.V1.Auth.LOGOUT)
    public ResponseEntity<GlobalResponse<Meta, String>> logout(@RequestBody LogoutRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.logout(request));
    }

    @Operation(summary = "Forgot Password", description = "Send password reset instructions to user's email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset instructions sent successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping(Endpoint.V1.Auth.FORGOT_PASSWORD)
    public ResponseEntity<GlobalResponse<Meta, String>> forgotUserPassword(
            @PathVariable String studentCode
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.forgotPassword(studentCode));
    }

    @PostMapping(Endpoint.V1.Auth.REFRESH)
    public ResponseEntity<GlobalResponse<Meta, RefreshResponse>> refreshToken(@RequestBody RefreshRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.refreshToken(request));
    }
}
