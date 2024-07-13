package com.example.hitproduct.config;

import com.example.hitproduct.domain.entity.Role;
import com.example.hitproduct.domain.entity.User;
import com.example.hitproduct.repository.RoleRepository;
import com.example.hitproduct.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Bean
    public UserDetailsService userDetailsService() {
        return studentCode ->
                userRepository.findByStudentCode(studentCode)
                              .orElseThrow(() -> new UsernameNotFoundException(studentCode));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecureRandom secureRandom() {
        return new SecureRandom();
    }

    @Bean
    public CommandLineRunner initSystem() {
        List<String> defaultRoles = List.of("ROLE_USER", "ROLE_ADMIN");

        defaultRoles.forEach(item -> {
            Optional<Role> found = roleRepository.findByName(item);
            if (found.isEmpty()) {
                roleRepository.save(Role.builder().name(item).build());
            }
        });


        final String studentCode = "2021608538";
        final String password = "B@i/wy2x";
        return args -> {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
            Optional<User> found = userRepository.findByStudentCode(studentCode);
            if (found.isEmpty()) {
                User user = User.builder()
                                .studentCode(studentCode)
                                .password(passwordEncoder().encode(password))
                                .role(adminRole)
                                .fullName("Hoang Nguyen Viet")
                                .email("hoangnv.swe@gmail.com")
                                .build();
                userRepository.save(user);
            }
        };
    }
}
