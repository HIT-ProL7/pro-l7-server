package com.example.hitproduct.controller;
/*
 * @author HongAnh
 * @created 14 / 06 / 2024 - 6:54 PM
 * @project HitProduct
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.constant.Endpoint;
import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.request.LoginRequest;
import com.example.hitproduct.domain.dto.request.UpdateUserRequest;
import com.example.hitproduct.domain.dto.request.UserRequest;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthService authService;
    UserMapper userMapper;
    AuthenticationManager authenticationManager;
    JwtUtils jwtUtils;

    @PostMapping(Endpoint.V1.Auth.REGISTER)
    public ResponseEntity<GlobalResponse<Meta, UserResponse>> registerUser(
            @RequestBody @Valid UserRequest request
    ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(request));
    }

    @PostMapping(Endpoint.V1.Auth.LOGIN)
    public ResponseEntity<GlobalResponse<Meta, AuthResponse>> login(
            @Valid @RequestBody LoginRequest request
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.login(request));
    }

    @PutMapping("/auth/forgot")
    public ResponseEntity<UserResponse> forgotUserPassword(@RequestBody UpdateUserRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
