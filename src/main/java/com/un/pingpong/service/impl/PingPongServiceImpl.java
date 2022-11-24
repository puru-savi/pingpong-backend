package com.un.pingpong.service.impl;


import com.google.gson.Gson;
import com.un.pingpong.exception.PingPongException;
import com.un.pingpong.model.PingPong;
import com.un.pingpong.model.User;
import com.un.pingpong.model.UserScore;
import com.un.pingpong.pojos.pinpong.HighestScoreResponse;
import com.un.pingpong.pojos.pinpong.Result;
import com.un.pingpong.pojos.pinpong.ResultData;
import com.un.pingpong.repository.PingPongRepository;
import com.un.pingpong.repository.UserRepository;
import com.un.pingpong.repository.UserScoreRepository;
import com.un.pingpong.service.PingPongService;
import com.un.pingpong.utils.CSVHelper;
import com.un.pingpong.utils.UserScoreConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class PingPongServiceImpl implements PingPongService {
    @Autowired
    PingPongRepository pingPongRepository;
    @Autowired
    UserScoreRepository userScoreRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CSVHelper csvHelper;

    @Override
    public Result getResult(List<String> inputList, Long userId) {
        Result result = new Result();
        List<ResultData> resultList = new ArrayList<>();
        int smashCount = 0;
        for (String input : inputList) {
            ResultData data = new ResultData();
            if (input.toUpperCase().startsWith("PING") && input.contains(" ") && input.toUpperCase().endsWith("PONG")) {
                data.setInput(input);
                data.setOutput("Smash");
                smashCount++;
            } else if (input.equalsIgnoreCase("ping")) {
                data.setInput(input);
                data.setOutput("pong");
            } else if (input.equalsIgnoreCase("pong")) {
                data.setInput(input);
                data.setOutput("ping");
            } else if (input.equalsIgnoreCase("smash")) {
                data.setInput(input);
                data.setOutput("");
            } else {
                data.setInput(input);
                data.setOutput("Bad");
            }
            resultList.add(data);
        }
        result.setRecentScore(smashCount);
        result.setResultDataList(resultList);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent())
            result.setUserName(user.get().getUserName());

        PingPong pingPong = new PingPong();
        pingPong.setPingPongResult(new Gson().toJson(result));
        pingPong.setSmashCount(smashCount);
        pingPong.setUserId(userId);
        pingPongRepository.save(pingPong);

        UserScore userScore = new UserScore();
        userScore.setUserId(userId);
        userScore.setHighestScore(smashCount);
        userScore.setCreatedDate(new Date());
        userScoreRepository.save(userScore);
        return result;
    }

    @Override
    public List<HighestScoreResponse> getHighestScore() throws PingPongException {
        List<HighestScoreResponse> highestScoreResponseList = new ArrayList<>();
        try {
            List<Object> userScores = userScoreRepository.findMaxHighestScoreGroupByUserId();
            for (int i = 0; i < userScores.size(); i++) {
                HighestScoreResponse highestScoreResponse = new HighestScoreResponse();
                highestScoreResponse.setHighestScore((Integer) ((Object[]) userScores.get(i))[0]);
                highestScoreResponse.setUserId((Long) ((Object[]) userScores.get(i))[1]);
                highestScoreResponse.setUsername((String) ((Object[]) userScores.get(i))[2]);
                highestScoreResponseList.add(highestScoreResponse);
            }
        } catch (Exception e) {
throw new PingPongException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }

        Collections.sort(highestScoreResponseList, new Comparator<HighestScoreResponse>() {
            @Override
            public int compare(HighestScoreResponse a1, HighestScoreResponse a2) {
                return a2.getHighestScore() - a1.getHighestScore() ;
            }
        });

        return highestScoreResponseList;
    }

    @Override
    public void shutdownPingpong() throws PingPongException {
        List<UserScore> userScores = userScoreRepository.findAll();
        try {
            String home = System.getProperty("user.home");
            File f = new File(home + File.separator + "Desktop" + File.separator + "pingpong.csv");
            csvHelper.writeToCsvFile(UserScoreConverter.convertUserScoreList(userScores), f);
        } catch (IOException e) {
            e.printStackTrace();
            throw new PingPongException(HttpStatus.EXPECTATION_FAILED.value(), e.getMessage());
        }
    }
}
