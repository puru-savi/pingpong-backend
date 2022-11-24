package com.un.pingpong.service.impl;

import com.un.pingpong.exception.PingPongException;
import com.un.pingpong.model.User;
import com.un.pingpong.model.UserScore;
import com.un.pingpong.model.UserSession;
import com.un.pingpong.pojos.user.UserToken;
import com.un.pingpong.repository.UserRepository;
import com.un.pingpong.repository.UserScoreRepository;
import com.un.pingpong.repository.UserSessionRepository;
import com.un.pingpong.service.UserService;
import com.un.pingpong.utils.CSVHelper;
import com.un.pingpong.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserScoreRepository userScoreRepository;

    @Autowired
    UserSessionRepository userSessionRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void createDefaultUsers() {
        List<User> users= new ArrayList<>();
        User user1 = new User();
        user1.setUserName("Puru");
        user1.setPassword("Puru123");
        users.add(user1);
        User user2 = new User();
        user2.setUserName("Addi");
        user2.setPassword("Addi123");
        users.add(user2);
        User user3 = new User();
        user3.setUserName("Archu");
        user3.setPassword("Archu123");
        users.add(user3);
        userRepository.saveAll(users);
        log.info("users created successfully!");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void saveHighScoreInDB() {

        try {
            File f = new File(System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "pingpong.csv");
            final InputStream targetStream =
                    new DataInputStream(new FileInputStream(f));
            List<UserScore> scoreList = CSVHelper.csvToTutorials(targetStream);
            userScoreRepository.saveAll(scoreList);
           log.info("{} UsersScores added successfully!",scoreList.size());

        } catch (Exception e) {
            log.warn("file not found! location: {}",System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "pingpong.csv");
        }
    }

    @Override
    public UserToken login(String userName, String password) throws PingPongException {
        User user = userRepository.findByUserName(userName);
        if(user == null)
            throw new PingPongException(HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase());
        if(password.equals(user.getPassword()))
        {
            UserToken userToken = new UserToken();
            user.setId(user.getId());
            userToken.setToken(TokenUtils.createJWT(user.getId(),userName));
            userToken.setUsername(userName);
            userToken.setId(user.getId());
            UserSession userSession= new UserSession();
            userSession.setUserId(user.getId());
            userSession.setToken(userToken.getToken());
            userSessionRepository.save(userSession);
            return userToken;
        }else {
             throw new PingPongException(HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }
    }


}
