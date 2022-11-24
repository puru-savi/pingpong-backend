package com.un.pingpong.service;


import com.un.pingpong.exception.PingPongException;
import com.un.pingpong.pojos.pinpong.HighestScoreResponse;
import com.un.pingpong.pojos.pinpong.Result;

import java.util.List;

public interface PingPongService {
    Result getResult(List<String> inputList, Long userId);
    List<HighestScoreResponse> getHighestScore() throws PingPongException;
    void shutdownPingpong() throws PingPongException;
}