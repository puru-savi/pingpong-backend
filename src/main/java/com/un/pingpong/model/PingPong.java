package com.un.pingpong.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "pingpong")
public class PingPong {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    long userId;
    @Column(length = 5000)
    String pingPongResult;
    int smashCount;
}
