package com.un.pingpong.repository;

import com.un.pingpong.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    UserSession findAllByTokenAndUserId(String header, Long id);
}
