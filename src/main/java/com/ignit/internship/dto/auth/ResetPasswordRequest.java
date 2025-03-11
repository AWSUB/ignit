package com.ignit.internship.dto.auth;

public class ResetPasswordRequest {

    private String token;

    private String password;

    public ResetPasswordRequest(String token, String password) {
        this.token = token;
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public String getPassword() {
        return password;
    }
}
