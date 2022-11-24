package com.un.pingpong.service;


import com.un.pingpong.exception.PingPongException;
import com.un.pingpong.model.UserSession;
import com.un.pingpong.pojos.pinpong.Result;
import com.un.pingpong.pojos.user.UserToken;

public interface UserService {
    UserToken login(String userName, String password) throws PingPongException;


}