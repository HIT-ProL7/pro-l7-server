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
        String ERR_NOT_LOGIN          = "exception.auth.not-login";
        String ERR_MISSING_PREFIX     = "exception.auth.missing-prefix";
        String ERR_EXPIRED_SESSION    = "exception.auth.expired-session";
        String ERR_INVALID_TOKEN      = "exception.auth.invalid-token";
        String ERR_UNSUPPORTED_TOKEN  = "exception.auth.unsupported-token";
    }
    
    interface User {
        String ERR_NOT_FOUND = "exception.user.notFound";
        String MISMATCHED_CONFIRM_PASSWORD = "exception.user.mismatched-confirm-password";
        String MISMATCHED_OLD_PASSWORD  = "exception.user.mismatched-old-password";
        String UNAUTHORIZED = "exception.user.unauthorized";
        String NOT_FOUND_IN_CLASS = "exception.user.notFoundInClass";
    }

    interface Classroom {
        String ERR_EXISTS_CLASSNAME = "exception.classroom.exist";
        String ERR_NOT_FOUND = "exception.classroom.not-found";
        String ERR_FORBIDDEN = "exception.classroom.forbidden";
        String ERR_NOTFOUND_BY_ID = "exception.classroom.not-found";
    }
}
