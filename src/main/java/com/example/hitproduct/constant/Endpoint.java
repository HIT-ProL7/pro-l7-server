package com.example.hitproduct.constant;

public interface Endpoint {

    interface V1 {
        String PREFIX = "/api/v1";

        interface User {
            String PREFIX = V1.PREFIX + "/users";
            String ME = PREFIX + "/me";
            String GET_LIST = PREFIX;
            String UPDATE_INFO = PREFIX + "/update-info";
            String CHANGE_PASSWORD = PREFIX + "/change-password";
        }

        interface Auth {
            String PREFIX = V1.PREFIX + "/auth";
            String REGISTER = PREFIX + "/register";
            String LOGIN = PREFIX + "/login";
        }

        interface Classroom{
            String PREFIX = V1.PREFIX + "/classrooms";
            String CREATE = PREFIX + "/create";
            String ADD_MEMBER = PREFIX + "/add-member";
            String ADD_LEADER = PREFIX + "/add-leader";
        }
    }
}
