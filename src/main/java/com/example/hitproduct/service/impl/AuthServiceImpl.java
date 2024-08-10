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
import com.example.hitproduct.domain.dto.request.*;
import com.example.hitproduct.domain.dto.response.AuthResponse;
import com.example.hitproduct.domain.dto.response.RefreshResponse;
import com.example.hitproduct.domain.dto.response.UserResponse;
import com.example.hitproduct.domain.entity.InvalidatedToken;
import com.example.hitproduct.domain.entity.Role;
import com.example.hitproduct.domain.entity.User;
import com.example.hitproduct.domain.mapper.UserMapper;
import com.example.hitproduct.exception.AlreadyExistsException;
import com.example.hitproduct.exception.AppException;
import com.example.hitproduct.exception.NotFoundException;
import com.example.hitproduct.job.AutoMailer;
import com.example.hitproduct.repository.InvalidatedTokenRepository;
import com.example.hitproduct.repository.RoleRepository;
import com.example.hitproduct.repository.UserRepository;
import com.example.hitproduct.security.jwt.JwtUtils;
import com.example.hitproduct.service.AuthService;
import com.example.hitproduct.util.RandomUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class AuthServiceImpl implements AuthService {
    UserRepository             userRepository;
    RoleRepository             roleRepository;
    InvalidatedTokenRepository tokenRepository;
    UserMapper                 userMapper;
    PasswordEncoder            passwordEncoder;
    AuthenticationManager      authenticationManager;
    JwtUtils                   jwtUtils;
    RandomUtil                 randomUtil;
    AutoMailer                 mailer;

    @NonFinal
    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;

    @NonFinal
    @Value("${auth.token.expirationInMils}")
    private int jwtExpirationTime;

    @NonFinal
    @Value("${auth.token.refreshInMils}")
    private int jwtRefreshTime;

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
        String refreshToken = jwtUtils.generateRefreshToken(authentication);
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
                        .refreshToken(refreshToken)
                        .roles(roles)
                        .loggedInUser(UserMapper.INSTANCE.toUserResponse(loggedInUser))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponse<Meta, String> forgotPassword(String studentCode) {
        User user = userRepository.findByStudentCode(studentCode)
                                  .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND));

        String newPassword = randomUtil.generatePassword();
        MailDTO mailDTO = MailDTO.builder()
                                 .to(user.getEmail())
                                 .subject("Đặt lại mật khẩu cho tài khoản của bạn")
                                 .text("Xin chào " + user.getFullName() + " ,\n" +
                                       "Bạn nhận được email này vì chúng tôi đã nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn.\n" +
                                       "Dưới đây là mật khẩu mới của bạn: " + newPassword + "\n" +
                                       "Nếu bạn có bất kỳ câu hỏi nào, vui lòng liên hệ với bộ phận hỗ trợ của chúng tôi.\n" +
                                       "Trân trọng,\nHIT ProL7").build();

        try{
            mailer.send(mailDTO);
            user.setPassword(passwordEncoder.encode(newPassword));

            userRepository.save(user);

            return GlobalResponse
                    .<Meta, String>builder()
                    .meta(Meta.builder().status(Status.SUCCESS).build())
                    .data("Vui lòng kiểm tra email của bạn để nhận mật khẩu mới")
                    .build();
        }catch (MailException ex) {
            log.error("Error sending email: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("Unexpected error: {}", ex.getMessage());
        }

        return GlobalResponse
                .<Meta, String>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(null)
                .build();
    }

    @Override
    public GlobalResponse<Meta, String> logout(LogoutRequest request) {
        try {
            var accessClaim = Jwts.parserBuilder()
                                  .setSigningKey(key())
                                  .build()
                                  .parseClaimsJws(request.getAccessToken())
                                  .getBody();

            var refreshClaim = Jwts.parserBuilder()
                                   .setSigningKey(key())
                                   .build()
                                   .parseClaimsJws(request.getRefreshToken())
                                   .getBody();


            tokenRepository.save(InvalidatedToken
                    .builder()
                    .id(accessClaim.getId())
                    .expirationTime(accessClaim.getExpiration())
                    .build()
            );

            tokenRepository.save(InvalidatedToken
                    .builder()
                    .id(refreshClaim.getId())
                    .expirationTime(refreshClaim.getExpiration())
                    .build()
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return GlobalResponse
                .<Meta, String>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data("Đăng xuất thành công")
                .build();
    }

    @Override
    public GlobalResponse<Meta, RefreshResponse> refreshToken(RefreshRequest request) {
        String id = jwtUtils.getJwtIdFromToken(request.getToken());
        Date expTime = jwtUtils.getExpirationTimeFromToken(request.getToken());
        String username = jwtUtils.getUserNameFromToken(request.getToken());

        tokenRepository.save(InvalidatedToken.builder().id(id).expirationTime(expTime).build());

        if (!expTime.after(new Date())) {
            throw new AppException(ErrorMessage.Auth.ERR_EXPIRED_SESSION);
        }

        User user = userRepository.findByStudentCode(username).orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND));


        String accessToken = createToken(user, jwtExpirationTime);
        String refreshToken = request.getToken();

        return GlobalResponse.<Meta, RefreshResponse>builder()
                             .meta(Meta.builder().status(Status.SUCCESS).build())
                             .data(RefreshResponse
                                     .builder()
                                     .accessToken(accessToken)
                                     .refreshToken(refreshToken)
                                     .build())
                             .build();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    private String createToken(User user, int time) {
        List<String> roles = user.getAuthorities()
                                 .stream()
                                 .map(GrantedAuthority::getAuthority)
                                 .collect(Collectors.toList());

        return Jwts.builder().setSubject(user.getUsername())
                   .setId(UUID.randomUUID().toString())
                   .claim("role", roles)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date((new Date()).getTime() + time))
                   .signWith(key(), SignatureAlgorithm.HS256)
                   .compact();
    }
}
