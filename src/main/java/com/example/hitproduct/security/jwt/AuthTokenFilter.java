package com.example.hitproduct.security.jwt;
/*
 * @author HongAnh
 * @created 14 / 06 / 2024 - 6:31 PM
 * @project HitProduct
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.constant.ErrorMessage;
import com.example.hitproduct.exception.AppException;
import com.example.hitproduct.repository.InvalidatedTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    private static final Logger logger      = LoggerFactory.getLogger(AuthTokenFilter.class);

    @NonFinal
    private              String AUTH_PREFIX = "Bearer ";
    @NonFinal
    private              String AUTH_HEADER = "Authorization";

    JwtUtils                   jwtUtils;
    UserDetailsService         userDetailsService;
    InvalidatedTokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateToken(jwt)) {
                String username = jwtUtils.getUserNameFromToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                String jwtID = jwtUtils.getJwtIdFromToken(jwt);

                if (tokenRepository.existsById(jwtID)) {
                    throw new AppException(ErrorMessage.Auth.ERR_INVALID_TOKEN);
                }

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication : {}", e.getMessage());
        }
        filterChain.doFilter(request, response);

    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(AUTH_HEADER);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(AUTH_PREFIX)) {
            return headerAuth.substring(AUTH_PREFIX.length());
        }
        return null;
    }
}
