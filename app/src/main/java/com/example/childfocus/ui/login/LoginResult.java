package com.example.childfocus.ui.login;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {
    private boolean success;
    private String token;

    private LoginResult(boolean success, String token) {
        this.success = success;
        this.token = token;
    }

    static LoginResult succeed(String token) {
        return new LoginResult(true, token);
    }

    static LoginResult failed() {
        return new LoginResult(false, null);
    }

    public boolean success() {
        return success;
    }

    public String getToken() {
        return token;
    }
}
