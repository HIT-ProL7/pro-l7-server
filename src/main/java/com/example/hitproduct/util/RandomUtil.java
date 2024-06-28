package com.example.hitproduct.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RandomUtil {
    private final String UPPERCASE          = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String LOWERCASE          = "abcdefghijklmnopqrstuvwxyz";
    private final String DIGITS             = "0123456789";
    private final String SPECIAL_CHARACTERS = "!@#$%^&*()-_+=<>?/{}~|";
    private final String ALL_CHARACTERS     = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARACTERS;
    private final int    PASSWORD_LENGTH    = 8;

    private final SecureRandom random;

    public String generatePassword() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        password.append(getRandomCharacter(UPPERCASE));
        password.append(getRandomCharacter(LOWERCASE));
        password.append(getRandomCharacter(DIGITS));
        password.append(getRandomCharacter(SPECIAL_CHARACTERS));

        for (int i = password.length(); i < PASSWORD_LENGTH; i++) {
            password.append(getRandomCharacter(ALL_CHARACTERS));
        }

        List<Character> passwordChars = new ArrayList<>();
        for (char c : password.toString().toCharArray()) {
            passwordChars.add(c);
        }
        Collections.shuffle(passwordChars);

        StringBuilder shuffledPassword = new StringBuilder();
        for (char c : passwordChars) {
            shuffledPassword.append(c);
        }

        return shuffledPassword.toString();
    }

    private char getRandomCharacter(String characters) {
        int index = random.nextInt(characters.length());
        return characters.charAt(index);
    }
}
