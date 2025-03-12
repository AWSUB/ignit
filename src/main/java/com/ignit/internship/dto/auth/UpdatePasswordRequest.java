package com.ignit.internship.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdatePasswordRequest {

    private String username;

    private String oldPassword;

    @NotBlank
    @Size(min = 8)
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
