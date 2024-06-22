package com.example.hitproduct.constant;
/*
 * @author HongAnh
 * @created 16 / 06 / 2024 - 10:55 AM
 * @project HitProduct
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

public interface ValidationMessage {

    interface Login{
        String STUDENT_CODE_INVALID = "Student code at least size of 10";
        String PASSWORD_INVALID = "Password must be minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character";
    }

    interface User{
        String STUDENT_CODE_INVALID = "Student code at least size of 10 and a numeric";
        String STUDENT_CODE_NUMERIC = "Student code must be numeric";
        String PASSWORD_INVALID = "Password must be minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character";
    }
}
