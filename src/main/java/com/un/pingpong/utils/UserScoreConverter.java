package com.un.pingpong.utils;

import com.un.pingpong.model.UserScore;

import java.util.ArrayList;
import java.util.List;
public class UserScoreConverter {

    public static List<String[]> convertUserScoreList(List<UserScore> list) {
        List<String[]> result = new ArrayList<>();
        for (UserScore p : list) {
            result.add(convertUserScore(p));
        }

        return result;
    }

    private static String[] convertUserScore(UserScore p) {
        String[] userScoreAttributes = new String[3];
        userScoreAttributes[0] = String.valueOf(p.getUserId());
        userScoreAttributes[1] = String.valueOf(p.getHighestScore());
        userScoreAttributes[2] = String.valueOf(p.getCreatedDate());
        return userScoreAttributes;
    }
}
