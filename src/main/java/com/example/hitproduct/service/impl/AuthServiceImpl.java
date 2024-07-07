package com.example.hitproduct.service.impl;
/*
 * @author HongAnh
 * @created 14 / 06 / 2024 - 6:20 PM
 * @project HitProduct
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.constant.ErrorMessage;
import com.example.hitproduct.domain.dto.global.BlankData;
import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.global.Status;
import com.example.hitproduct.domain.dto.request.AddUserRequest;
import com.example.hitproduct.domain.dto.request.LoginRequest;
import com.example.hitproduct.domain.dto.request.UpdateInfoRequest;
import com.example.hitproduct.domain.dto.response.AuthResponse;
import com.example.hitproduct.domain.dto.response.UserResponse;
import com.example.hitproduct.domain.entity.Role;
import com.example.hitproduct.domain.entity.User;
import com.example.hitproduct.domain.mapper.UserMapper;
import com.example.hitproduct.exception.AlreadyExistsException;
import com.example.hitproduct.exception.AppException;
import com.example.hitproduct.job.AutoMailer;
import com.example.hitproduct.repository.RoleRepository;
import com.example.hitproduct.repository.UserRepository;
import com.example.hitproduct.security.jwt.JwtUtils;
import com.example.hitproduct.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
    UserRepository        userRepository;
    RoleRepository        roleRepository;
    UserMapper            userMapper;
    PasswordEncoder       passwordEncoder;
    AuthenticationManager authenticationManager;
    JwtUtils              jwtUtils;

    @Override
    public GlobalResponse<Meta, UserResponse> register(AddUserRequest userRequest) {
        if (userRepository.existsByStudentCode(userRequest.getStudentCode())) {
            throw new AlreadyExistsException(ErrorMessage.Auth.ERR_EXISTS_USERNAME);
        }
        User user = userMapper.toUser(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        Role role = roleRepository.findByName("ROLE_USER").get();

        roleRepository.save(role);

        user.setRole(role);

        User savedUser = userRepository.save(user);

        return GlobalResponse
                .<Meta, UserResponse>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(userMapper.toUserResponse(savedUser))
                .build();
    }

    @Override
    public GlobalResponse<Meta, AuthResponse> login(
            LoginRequest request
    ) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getStudentCode(),
                        request.getPassword()
                )
        );

        String accessToken = jwtUtils.generateJwtTokenForUser(authentication);
        User loggedInUser = (User) authentication.getPrincipal();
        String roles = loggedInUser.getAuthorities().stream()
                                   .map(GrantedAuthority::getAuthority)
                                   .collect(Collectors.joining(","));

        return GlobalResponse
                .<Meta, AuthResponse>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(AuthResponse
                        .builder()
                        .accessToken(accessToken)
                        .roles(roles)
                        .loggedInUser(UserMapper.INSTANCE.toUserResponse(loggedInUser))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponse<Meta, BlankData> forgotPassword(String studentCode) {
        return null;
    }

}
