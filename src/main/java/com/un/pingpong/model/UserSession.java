package com.un.pingpong.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "usersession")
public class UserSession {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    long userId;
    String token;
    Date expiry;
}
