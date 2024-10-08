package com.example.hitproduct.domain.mapper;
/*
 * @author HongAnh
 * @created 14 / 06 / 2024 - 6:15 PM
 * @project HitProduct
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.domain.dto.request.UpdateInfoRequest;
import com.example.hitproduct.domain.dto.request.AddUserRequest;
import com.example.hitproduct.domain.dto.response.UserResponse;
import com.example.hitproduct.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(AddUserRequest request);

    @Mapping(target = "role", ignore = true)
    UserResponse toUserResponse(User user);

    User toUser(UpdateInfoRequest request);
}
