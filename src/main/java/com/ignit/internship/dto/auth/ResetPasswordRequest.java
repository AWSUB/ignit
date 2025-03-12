package com.ignit.internship.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ResetPasswordRequest {

    @NotBlank
    @Size(min = 8)
    private String password;

    public ResetPasswordRequest(String token, String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
