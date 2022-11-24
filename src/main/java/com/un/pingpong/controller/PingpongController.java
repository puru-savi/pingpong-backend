package com.un.pingpong.controller;

import com.un.pingpong.exception.PingPongException;
import com.un.pingpong.pojos.pinpong.HighestScoreResponse;
import com.un.pingpong.pojos.pinpong.Result;
import com.un.pingpong.service.PingPongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.un.pingpong.constants.Constants.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class PingpongController {

    @Autowired
    PingPongService pingPongService;

    @GetMapping(value = PPMATCH_URL)
    public Result getPPMatch(@RequestParam(value = "input") List<String> stringList, HttpServletRequest request) {
        Long userID = (Long) request.getAttribute("userId");
        return pingPongService.getResult(stringList,userID);
    }

    @GetMapping(value = HIGHSCORE_URL)
    public List<HighestScoreResponse> getHighScore( HttpServletRequest request) throws PingPongException {
        return pingPongService.getHighestScore();
    }

    @GetMapping(value = SHUTDOWN_URL)
    public void shutdown(HttpServletRequest request) throws PingPongException {
         pingPongService.shutdownPingpong();
    }

    @GetMapping(value = HEALTH_URL)
    public Map health(HttpServletRequest request) throws PingPongException {
        Map<String,String> map= new HashMap<>();
        map.put("status","ON");
    return  map;
    }
}
