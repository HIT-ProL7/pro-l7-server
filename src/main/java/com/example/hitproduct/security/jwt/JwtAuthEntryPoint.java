package com.example.hitproduct.security.jwt;
/*
 * @author HongAnh
 * @created 14 / 06 / 2024 - 6:52 PM
 * @project HitProduct
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.constant.ErrorMessage;
import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.global.Status;
import com.example.hitproduct.util.MessageSourceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper      objectMapper;
    private final MessageSourceUtil messageSourceUtil;
    private final JwtUtils          jwtUtils;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        final String BEARER_PREFIX = "Bearer ";
        final String authHeader = request.getHeader("Authorization");

        String message = null;
        if (authHeader == null || authHeader.isBlank()) {
            message = ErrorMessage.Auth.ERR_NOT_LOGIN;
        }

        if (authHeader != null && !authHeader.startsWith(BEARER_PREFIX)) {
            message = ErrorMessage.Auth.ERR_MISSING_PREFIX;
        }

        if (message == null) {
            message = jwtUtils.checkToken(authHeader.substring(BEARER_PREFIX.length()));
        }

        if (message == null) {
            message = ErrorMessage.Auth.ERR_INVALID_TOKEN;
        }
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        GlobalResponse<Meta, Void> responseBody = GlobalResponse.<Meta, Void>builder()
                                                                .meta(Meta.builder()
                                                                          .status(Status.ERROR)
                                                                          .message(messageSourceUtil.getLocalizedMessage(message))
                                                                          .build()
                                                                )
                                                                .build();

        objectMapper.writeValue(response.getOutputStream(), responseBody);
    }
}