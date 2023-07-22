package com.example.entity;

import lombok.Data;

@Data
public class User {
    private int userId;
    private String userName;
    private String userPassword;
    private String userState;//默认正常3
//    private String userPicture;
    private int userPoint;//初始账号默认1000

    public User() {
    }

    public User(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }
}
