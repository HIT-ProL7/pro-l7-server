package com.example.hitproduct.controller;
/*
 * @author HongAnh
 * @created 14 / 06 / 2024 - 8:54 PM
 * @project HitProduct
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.base.RestApiV1;
import com.example.hitproduct.constant.Endpoint;
import com.example.hitproduct.domain.dto.request.UpdateUserRequest;
import com.example.hitproduct.domain.dto.response.UserResponse;
import com.example.hitproduct.service.IService.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    IUserService userService;

    @GetMapping(Endpoint.V1.User.GET_LIST)
    public ResponseEntity<UserResponse> getUser() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser());
    }

    @PutMapping(Endpoint.V1.User.UPDATE_INFO)
    public ResponseEntity<UserResponse> updatedUser(@RequestBody UpdateUserRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(request));
    }
}
