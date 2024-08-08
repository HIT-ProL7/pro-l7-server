package com.example.hitproduct.security;
/*
 * @author HongAnh
 * @created 14 / 06 / 2024 - 6:25 PM
 * @project HitProduct
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.constant.Endpoint;
import com.example.hitproduct.constant.ErrorMessage;
import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.global.Status;
import com.example.hitproduct.repository.UserRepository;
import com.example.hitproduct.security.jwt.AuthTokenFilter;
import com.example.hitproduct.security.jwt.JwtAuthEntryPoint;
import com.example.hitproduct.service.AuthService;
import com.example.hitproduct.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {
    String CATCH_ALL_WILDCARD = "/**";

    JwtAuthEntryPoint      authEntryPoint;
    AuthTokenFilter        authTokenFilter;
    AuthenticationProvider authenticationProvider;
    ObjectMapper           objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(exception -> {
                exception.authenticationEntryPoint(authEntryPoint);
                exception.accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    String message = "Access denied! You do not have permission to access this resource.";
                    GlobalResponse<Meta, Void> responseBody = GlobalResponse.<Meta, Void>builder()
                                                                            .meta(Meta.builder()
                                                                                      .status(Status.ERROR)
                                                                                      .message(message)
                                                                                      .build()
                                                                            )
                                                                            .build();
                    objectMapper.writeValue(response.getOutputStream(), responseBody);

                });
            })
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                            .requestMatchers(Endpoint.V1.Auth.REGISTER).hasRole("ADMIN")
                            .requestMatchers(Endpoint.V1.Auth.PREFIX + CATCH_ALL_WILDCARD).permitAll()
                            .requestMatchers(HttpMethod.GET, Endpoint.V1.Classroom.PREFIX).hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, Endpoint.V1.Classroom.CREATE).hasRole("ADMIN")
//                    .requestMatchers(Endpoint.V1.Lesson.PREFIX + CATCH_ALL_WILDCARD).hasRole("ADMIN")
                            .anyRequest().authenticated()
            )
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
