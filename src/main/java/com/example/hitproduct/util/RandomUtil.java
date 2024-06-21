package com.example.hitproduct.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomUtil {
    private static final String       UPPERCASE          = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String       LOWERCASE          = "abcdefghijklmnopqrstuvwxyz";
    private static final String       DIGITS             = "0123456789";
    private static final String       SPECIAL_CHARACTERS = "!@#$%^&*()-_+=<>?/{}~|";
    private static final String       ALL_CHARACTERS     = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARACTERS;
    private static final int          PASSWORD_LENGTH    = 8;
    private static       SecureRandom random             = new SecureRandom();

    public static String generatePassword() {
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

        // Convert back to String
        StringBuilder shuffledPassword = new StringBuilder();
        for (char c : passwordChars) {
            shuffledPassword.append(c);
        }

        return shuffledPassword.toString();
    }

    private static char getRandomCharacter(String characters) {
        int index = random.nextInt(characters.length());
        return characters.charAt(index);
    }
}
