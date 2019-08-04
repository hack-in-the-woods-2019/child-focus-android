package com.example.childfocus.ui.login;

public class UserToken {

    private static UserToken INSTANCE;

    private String token;

    private UserToken() {}

    public static UserToken getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserToken();
        }

        return INSTANCE;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
