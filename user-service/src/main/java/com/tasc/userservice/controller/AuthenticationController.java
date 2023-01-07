package com.tasc.userservice.controller;

import com.tasc.userservice.model.request.LoginRequest;
import com.tasc.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.tass.microservice.model.ApplicationException;
import vn.tass.microservice.model.BaseResponseV2;
import vn.tass.microservice.redis.dto.UserLoginDTO;

@RestController
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public BaseResponseV2<UserLoginDTO> login(@RequestBody LoginRequest request) throws ApplicationException {
        return userService.login(request);
    }
}
