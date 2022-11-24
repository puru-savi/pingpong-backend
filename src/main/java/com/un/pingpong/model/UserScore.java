package com.un.pingpong.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "userscore")
@Data
public class UserScore {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    long userId;
    int highestScore;
    Date createdDate;
}
