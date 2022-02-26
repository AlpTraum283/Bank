package com.netcracker.model.dto.rest;

public class LoginPostDto {

    private String userName;

    private String password;

    public LoginPostDto() {
    }

    public LoginPostDto(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
