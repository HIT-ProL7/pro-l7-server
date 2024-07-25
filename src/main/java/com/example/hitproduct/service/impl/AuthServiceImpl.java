package com.example.hitproduct.service.impl;
/*
 * @author HongAnh
 * @created 14 / 06 / 2024 - 6:20 PM
 * @project HitProduct
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.constant.ErrorMessage;
import com.example.hitproduct.domain.dto.MailDTO;
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
import com.example.hitproduct.exception.NotFoundException;
import com.example.hitproduct.job.AutoMailer;
import com.example.hitproduct.repository.RoleRepository;
import com.example.hitproduct.repository.UserRepository;
import com.example.hitproduct.security.jwt.JwtUtils;
import com.example.hitproduct.service.AuthService;
import com.example.hitproduct.util.RandomUtil;
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
    RandomUtil            randomUtil;
    AutoMailer            mailer;

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
    public GlobalResponse<Meta, UserResponse> forgotPassword(String studentCode) {
        User user = userRepository.findByStudentCode(studentCode)
                .orElseThrow(()->new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND));

        String newPassword = randomUtil.generatePassword();
        MailDTO mailDTO = MailDTO.builder()
                                 .to(user.getEmail())
                                 .subject("Đặt lại mật khẩu cho tài khoản của bạn")
                                 .text("<h2>Xin chào "+ user.getUsername()+" ,</h2>\n" +
                                       "    <p>Bạn nhận được email này vì chúng tôi đã nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn.</p>\n" +
                                       "    <p>Dưới đây là mật khẩu mới của bạn: </p>\n" +
                                       "    <p><a href=\"\" target=\"_blank\">"+ newPassword +"</a></p>\n" +
                                       "    <p>Nếu bạn có bất kỳ câu hỏi nào, vui lòng liên hệ với bộ phận hỗ trợ của chúng tôi.</p>\n" +
                                       "    <p>Trân trọng,<br>HIT ProL7</p>").build();

        mailer.send(mailDTO);
        user.setPassword(passwordEncoder.encode(newPassword));

        user = userRepository.save(user);

        return GlobalResponse
                .<Meta, UserResponse>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(userMapper.toUserResponse(user))
                .build();
    }
}
