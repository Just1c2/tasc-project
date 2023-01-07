package com.tasc.userservice.controller;

import com.tasc.userservice.model.request.RegisterRequest;
import com.tasc.userservice.model.request.UpdateInfoRequest;
import com.tasc.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.tass.microservice.model.ApplicationException;
import vn.tass.microservice.model.BaseResponseV2;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public BaseResponseV2 register(@RequestBody RegisterRequest request) throws ApplicationException {
        return userService.register(request);
    }

    @PutMapping("/updateInfo")
    public BaseResponseV2 updateInfo(@RequestBody UpdateInfoRequest request,@RequestParam(name = "id") long id) throws ApplicationException {
        return userService.updateInfo(request, id);
    }
}
