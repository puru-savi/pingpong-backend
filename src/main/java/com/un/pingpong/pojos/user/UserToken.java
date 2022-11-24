package com.un.pingpong.pojos.user;


import lombok.Data;

import java.util.Date;

@Data
public class UserToken {
    String token;
    Date expiry;
    long id;
    String username;
}
