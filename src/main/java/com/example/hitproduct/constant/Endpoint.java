package com.example.hitproduct.constant;

public interface Endpoint {

    interface V1 {
        String PREFIX = "/api/v1";

        interface User {
            String PREFIX          = V1.PREFIX + "/users";
            String ME              = PREFIX + "/me";
            String GET_LIST        = PREFIX;
            String UPDATE_INFO     = PREFIX + "/update-info";
            String CHANGE_PASSWORD = PREFIX + "/change-password";
        }

        interface Auth {
            String PREFIX          = V1.PREFIX + "/auth";
            String REGISTER        = PREFIX + "/register";
            String LOGIN           = PREFIX + "/login";
            String FORGOT_PASSWORD = PREFIX + "/forgot-password/{studentCode}";
        }

        interface Classroom {
            String PREFIX = V1.PREFIX + "/classrooms";
            String CREATE = PREFIX + "/create";
            String CLOSE  = PREFIX + "/{classroomId}" + "/close";
            String UPDATE = PREFIX + "/{classroomId}";
            String GET_MEMBERS = PREFIX + "/{classroomId}" + "/members";
            String CLASSROOM_ID = PREFIX + "/{classroomId}";
            String ADD_MEMBER = CLASSROOM_ID + "/members";
            String MY_CLASS = PREFIX + "/my-class";
            String DELETE_MEMBER = CLASSROOM_ID + "/members" + "/{userId}";
            String EDIT_MEMBER_ROLE = CLASSROOM_ID + "/members" + "/{userId}/positions";
        }

        interface Lesson{
            String PREFIX = V1.PREFIX + "/lessons";
            String GET_LESSON_IN_CLASSROOM = PREFIX + "/classroom/{classroomId}";
            String LESSON_ID = PREFIX + "/{lessonId}";
        }
    }
}
