package com.tasc.userservice.model.request;

import lombok.Data;

@Data
public class UserRequest {

    private String username;
    private String password;
    private String rePassword;
}
