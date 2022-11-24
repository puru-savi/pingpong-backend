package com.un.pingpong.controller;

import com.un.pingpong.exception.PingPongException;
import com.un.pingpong.pojos.user.UserToken;
import com.un.pingpong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.un.pingpong.constants.Constants.LOGIN_URL;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = LOGIN_URL)
    public UserToken saveFruits(@RequestBody Map<String,String> requestData) throws PingPongException {
        return userService.login(requestData.get("userName"),requestData.get("password"));
    }

}
