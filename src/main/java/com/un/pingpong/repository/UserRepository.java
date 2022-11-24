package com.un.pingpong.repository;

import com.un.pingpong.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {
    User findByUserName(String userName);
}
