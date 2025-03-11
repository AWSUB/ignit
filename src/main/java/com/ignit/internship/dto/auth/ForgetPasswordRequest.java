package com.ignit.internship.dto.auth;

public class ForgetPasswordRequest {

    private String username;

    private String email;

    public ForgetPasswordRequest(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
