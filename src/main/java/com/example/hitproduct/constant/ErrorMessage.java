package com.example.hitproduct.constant;
/*
 * @author HongAnh
 * @created 14 / 06 / 2024 - 7:09 PM
 * @project HitProduct
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

public interface ErrorMessage {
    interface Auth {
        String ERR_EXISTS_USERNAME    = "exception.user.exist";
        String ALREADY_RESET_PASSWORD = "exception.auth.already-reset-password";
    }

    interface User {
        String ERR_NOT_FOUND               = "exception.user.notFound";
        String MISMATCHED_CONFIRM_PASSWORD = "exception.user.mismatched-confirm-password";
        String MISMATCHED_OLD_PASSWORD     = "exception.user.mismatched-old-password";
    }

    interface Classroom{
        String ERR_EXISTS_CLASSNAME = "exception.class.exist";
        String ERR_NOT_FOUND = "exception.classroom.not-found";
        String ERR_FORBIDDEN = "exception.classroom.forbidden";
    }
}
