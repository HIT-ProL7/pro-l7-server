package com.example.hitproduct.constant;

public interface Endpoint {

    interface V1 {
        String PREFIX = "/api/v1";

        interface User {
            String PREFIX = V1.PREFIX + "/users";
            String GET_LIST = PREFIX;
            String UPDATE_INFO = PREFIX + "/update";
        }
    }
}
