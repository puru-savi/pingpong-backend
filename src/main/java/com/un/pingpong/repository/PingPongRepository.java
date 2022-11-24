package com.un.pingpong.repository;

import com.un.pingpong.model.PingPong;
import com.un.pingpong.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PingPongRepository extends JpaRepository<PingPong, Long> {
}
