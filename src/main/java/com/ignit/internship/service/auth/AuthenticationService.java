package com.ignit.internship.service.auth;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ignit.internship.dto.auth.ForgetPasswordRequest;
import com.ignit.internship.dto.auth.ResetPasswordRequest;
import com.ignit.internship.dto.auth.UpdatePasswordRequest;
import com.ignit.internship.dto.auth.UserLoginRequest;
import com.ignit.internship.dto.auth.UserRegisterRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.exception.InvalidVerificationException;
import com.ignit.internship.model.auth.User;
import com.ignit.internship.repository.auth.UserRepository;
import com.ignit.internship.service.utils.EmailService;

@Service
public final class AuthenticationService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final EmailService emailService;

    @Value("${base.url}")
    private String baseUrl;

    public AuthenticationService(
        final UserRepository userRepository, 
        final BCryptPasswordEncoder passwordEncoder,
        final EmailService emailService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    //use front-end url for email
    public void register(UserRegisterRequest request) {
        User user = userRepository.save(new User(
            request.getUsername(),
            passwordEncoder.encode(request.getPassword()),
            request.getEmail(),
            new SimpleGrantedAuthority("ROLE_USER")
        ));

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Ignit User Verification");
        mailMessage.setText("Verify by clicking this link below:\n" + baseUrl + "/api/auth/verify?token=" + user.getVerificationToken());

        emailService.sendEmail(mailMessage);
    }

    public void verify(String token) throws Exception {
        User user = userRepository.findByVerificationToken(token).orElseThrow(() -> new InvalidVerificationException("Verification failed"));
        user.setEnabled(true);
        user.setVerificationToken(null);

        userRepository.save(user);
    }

    public User authenticate(UserLoginRequest request) throws Exception {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Password not match");
        }

        if (!user.isEnabled()) {
            throw new InvalidVerificationException("User not verified");
        }

        return user;
    }

    public User updatePassword(UpdatePasswordRequest request) throws Exception {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BadCredentialsException("Password not match");
        }

        if (!user.isEnabled()) {
            throw new InvalidVerificationException("User not verified");
        }

        user.setPassword(request.getNewPassword());

        return userRepository.save(user);
    }

    //use front-end url for email
    public void forgetPassword(ForgetPasswordRequest request) throws Exception {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.getEmail().equals(request.getEmail())) {
            throw new BadCredentialsException("Email not match");
        }

        user.setVerificationToken(UUID.randomUUID().toString());
        
        User savedUser = userRepository.save(user);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(savedUser.getEmail());
        mailMessage.setSubject("Ignit User Forgot Password");
        mailMessage.setText(
            "Change your password by clicking this link below:\n" + baseUrl + "/api/auth/reset-password?token=" + savedUser.getVerificationToken()
        );        
    }

    public void resetPassword(String token, ResetPasswordRequest request) throws IdNotFoundException {
        User user = userRepository.findByVerificationToken(token).orElseThrow(() -> new IdNotFoundException("User not found"));
        user.setVerificationToken(null);
        user.setPassword(request.getPassword());

        userRepository.save(user);        
    }
}
