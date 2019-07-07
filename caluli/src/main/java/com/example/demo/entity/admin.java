package com.example.demo.entity;

public class admin {
    private int id;
    private String userId;
    private String userPassword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return "admin{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }
}
