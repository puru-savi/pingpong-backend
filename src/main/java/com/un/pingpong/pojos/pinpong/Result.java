package com.un.pingpong.pojos.pinpong;

import lombok.Data;

import java.util.List;

@Data
public class Result {
    int recentScore;
    String userName;
    List<ResultData> resultDataList;
}
