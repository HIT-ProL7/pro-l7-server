package com.example.hitproduct.controller;
/*
 * @author HongAnh
 * @created 14 / 06 / 2024 - 8:54 PM
 * @project HitProduct
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.constant.Endpoint;
import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.request.ChangePasswordRequest;
import com.example.hitproduct.domain.dto.request.UpdateInfoRequest;
import com.example.hitproduct.domain.dto.response.UserResponse;
import com.example.hitproduct.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @GetMapping(Endpoint.V1.User.ME)
    public ResponseEntity<GlobalResponse<Meta, UserResponse>> getCurrentUser(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getCurrentUser(userDetails));
    }

    @PutMapping(Endpoint.V1.User.UPDATE_INFO)
    public ResponseEntity<GlobalResponse<Meta, UserResponse>> updateInfo(
            @RequestBody UpdateInfoRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.updateUser(request, userDetails));
    }

    @PatchMapping(Endpoint.V1.User.CHANGE_PASSWORD)
    public ResponseEntity<GlobalResponse<Meta, UserResponse>> changePassword(
            @RequestBody @Valid ChangePasswordRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.changePassword(request, userDetails));
    }
}
