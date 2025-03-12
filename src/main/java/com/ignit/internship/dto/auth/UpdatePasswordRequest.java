package com.ignit.internship.dto.auth;

public class UpdatePasswordRequest {

    private String username;

    private String oldPassword;

    private String newPassword;

    public UpdatePasswordRequest(String username, String oldPassword) {
        this.username = username;
        this.oldPassword = oldPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
