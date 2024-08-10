package com.example.hitproduct.service.impl;
/*
 * @author HongAnh
 * @created 14 / 06 / 2024 - 6:18 PM
 * @project HitProduct
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.constant.ErrorMessage;
import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.global.Status;
import com.example.hitproduct.domain.dto.request.ChangePasswordRequest;
import com.example.hitproduct.domain.dto.request.UpdateInfoRequest;
import com.example.hitproduct.domain.dto.response.UserResponse;
import com.example.hitproduct.domain.entity.User;
import com.example.hitproduct.domain.mapper.UserMapper;
import com.example.hitproduct.exception.AppException;
import com.example.hitproduct.exception.InternalServerErrorException;
import com.example.hitproduct.repository.UserRepository;
import com.example.hitproduct.service.CloudinaryService;
import com.example.hitproduct.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository    userRepository;
    UserMapper        userMapper;
    PasswordEncoder   passwordEncoder;
    CloudinaryService cloudinaryService;

    @Override
    public GlobalResponse<Meta, UserResponse> getUserByStudentCode(
            String studentCode
    ) {
        User user = userRepository.findByStudentCode(studentCode)
                                  .orElseThrow(() -> new UsernameNotFoundException(ErrorMessage.User.ERR_NOT_FOUND));

        UserResponse response = userMapper.toUserResponse(user);
        response.setRole(user.getRole().getName());

        return GlobalResponse
                .<Meta, UserResponse>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(response)
                .build();
    }

    @Override
    public GlobalResponse<Meta, UserResponse> updateUser(
            String studentCode,
            UpdateInfoRequest request
    ) {
        User user = userRepository.findByStudentCode(studentCode)
                                  .orElseThrow(() -> new UsernameNotFoundException(ErrorMessage.User.ERR_NOT_FOUND));

        if (request.fullName() != null) user.setFullName(request.fullName());
        if (request.email() != null) user.setEmail(request.email());
        if (request.description() != null) user.setDescription(request.description());
        if (request.githubUrl() != null) user.setGithubUrl(request.githubUrl());
        if (request.facebookUrl() != null) user.setFacebookUrl(request.facebookUrl());
        if (request.avatar() != null) {
            try {
                user.setAvatarUrl(cloudinaryService.uploadImage(request.avatar().getBytes()));
            } catch (Exception e) {
                throw new InternalServerErrorException("Failed to upload avatar image.");
            }
        }
        if (request.banner() != null) {
            try {
                user.setBannerUrl(cloudinaryService.uploadImage(request.banner().getBytes()));
            } catch (Exception e) {
                throw new InternalServerErrorException("Failed to upload banner image.");
            }
        }

        user = userRepository.save(user);

        return GlobalResponse
                .<Meta, UserResponse>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(userMapper.toUserResponse(userRepository.save(user)))
                .build();
    }

    @Override
    public GlobalResponse<Meta, UserResponse> changePassword(
            String studentCode,
            ChangePasswordRequest request
    ) {
        if (!request.newPassword().equals(request.confirmNewPassword()))
            throw new AppException(ErrorMessage.User.MISMATCHED_CONFIRM_PASSWORD);

        User foundUser = userRepository.findByStudentCode(studentCode)
                                       .orElseThrow(() -> new UsernameNotFoundException(ErrorMessage.User.ERR_NOT_FOUND));
        if (!passwordEncoder.matches(request.oldPassword(), foundUser.getPassword()))
            throw new AppException(ErrorMessage.User.MISMATCHED_OLD_PASSWORD);

        foundUser.setPassword(passwordEncoder.encode(request.newPassword()));

        return GlobalResponse
                .<Meta, UserResponse>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(userMapper.toUserResponse(userRepository.save(foundUser)))
                .build();
    }
}
