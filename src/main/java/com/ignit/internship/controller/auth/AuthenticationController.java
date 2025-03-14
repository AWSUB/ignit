package com.ignit.internship.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ignit.internship.dto.DefaultResponse;
import com.ignit.internship.dto.ResponseReturn;
import com.ignit.internship.dto.auth.ForgetPasswordRequest;
import com.ignit.internship.dto.auth.JwtTokenResponse;
import com.ignit.internship.dto.auth.ResetPasswordRequest;
import com.ignit.internship.dto.auth.UpdatePasswordRequest;
import com.ignit.internship.dto.auth.UserLoginRequest;
import com.ignit.internship.dto.auth.UserRegisterRequest;
import com.ignit.internship.service.auth.AuthenticationService;
import com.ignit.internship.service.auth.JwtTokenService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    
    private final AuthenticationService authenticationService;

    private final JwtTokenService jwtTokenService;

    public AuthenticationController(
        final AuthenticationService authenticationService, 
        final JwtTokenService jwtTokenService
    ) {
        this.authenticationService = authenticationService;
        this.jwtTokenService = jwtTokenService;
    }

    @Operation(description = "User registration, token sent to email")
    @PostMapping("/register")
    public ResponseEntity<DefaultResponse<Object>> register(@RequestBody @Valid UserRegisterRequest request) {
        authenticationService.register(request);
        return ResponseReturn.ok(null);
    }

    @Operation(description = "User login")
    @PostMapping("/login")
    public ResponseEntity<DefaultResponse<JwtTokenResponse>> login(@RequestBody UserLoginRequest request) throws Exception {
        String token = jwtTokenService.buildToken(authenticationService.authenticate(request));
        return ResponseReturn.ok(new JwtTokenResponse(token));
    }

    @Operation(description = "New User verification")
    @GetMapping("/verify")
    public ResponseEntity<DefaultResponse<JwtTokenResponse>> verify(@RequestParam String token) throws Exception {
        authenticationService.verify(token);
        return ResponseReturn.ok(null);
    }

    @Operation(description = "Forget password for unlogged-in user, token sent to email")
    @PostMapping("/forget-password")
    public ResponseEntity<DefaultResponse<Object>> forgetPassword(@RequestBody ForgetPasswordRequest request) throws Exception {
        authenticationService.forgetPassword(request);
        return ResponseReturn.ok(null);
    }

    @Operation(description = "Update password for logged-in user")
    @PostMapping("/update-password")
    public ResponseEntity<DefaultResponse<Object>> updatePassword(@RequestBody UpdatePasswordRequest request) throws Exception {
        authenticationService.updatePassword(request);
        return ResponseReturn.ok(null);
    }

    @Operation(description = "Reset password for unlogged-in user, token retrieved from email")
    @PostMapping("/reset-password")
    public ResponseEntity<DefaultResponse<Object>> resetPassword(
        @Valid @RequestBody ResetPasswordRequest request,
        @RequestParam String token
    ) throws Exception {
        authenticationService.resetPassword(token, request);
        return ResponseReturn.ok(null);
    }
}
