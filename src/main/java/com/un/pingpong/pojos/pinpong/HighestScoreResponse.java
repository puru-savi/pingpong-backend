package com.un.pingpong.pojos.pinpong;

import lombok.Data;

@Data
public class HighestScoreResponse {
    long userId;
    String username;
    int highestScore;
}
