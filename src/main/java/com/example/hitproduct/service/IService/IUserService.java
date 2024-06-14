package com.example.hitproduct.service.IService;
/*
 * @author HongAnh
 * @created 14 / 06 / 2024 - 6:09 PM
 * @project HitProduct
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.domain.dto.request.UpdateUserRequest;
import com.example.hitproduct.domain.dto.request.UserRequest;
import com.example.hitproduct.domain.dto.response.UserResponse;

public interface IUserService {
    UserResponse getUser();
    UserResponse updateUser(UpdateUserRequest userRequest);
}
