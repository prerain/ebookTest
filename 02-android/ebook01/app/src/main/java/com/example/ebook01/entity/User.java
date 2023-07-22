package com.example.ebook01.entity;

public class User {
    private int userId;
    private String userName;
    private String userPassword;
    private String userState;//默认正常3
    private int userPoint;//初始账号默认1000

    public User() {
    }

    public User(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public User(int userId, String userName, String userPassword, String userState, int userPoint) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userState = userState;
        this.userPoint = userPoint;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }


    public int getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(int userPoint) {
        this.userPoint = userPoint;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userState='" + userState + '\'' +
                ", userPoint=" + userPoint +
                '}';
    }
}
