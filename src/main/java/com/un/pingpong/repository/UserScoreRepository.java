package com.un.pingpong.repository;

import com.un.pingpong.model.UserScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserScoreRepository extends JpaRepository<UserScore, Long> {
    @Query("SELECT max(us.highestScore) as highestScore, us.userId as userId , u.userName as username FROM UserScore as us , User u where u.id = us.userId group by us.userId")
    List<Object> findMaxHighestScoreGroupByUserId();
}

