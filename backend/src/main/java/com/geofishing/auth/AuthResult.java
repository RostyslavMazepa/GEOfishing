package com.geofishing.auth;

import com.geofishing.model.auth.User;

public class AuthResult {
    private User user;
    private AuthResultType resultType;

    public AuthResult(User user, AuthResultType resultType) {
        this.user = user;
        this.resultType = resultType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AuthResultType getResultType() {
        return resultType;
    }

    public void setResultType(AuthResultType resultType) {
        this.resultType = resultType;
    }

    public enum AuthResultType {FOUND, REGISTRED}
}
