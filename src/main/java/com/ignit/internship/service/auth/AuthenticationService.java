package com.ignit.internship.service.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ignit.internship.dto.auth.UserLoginRequest;
import com.ignit.internship.dto.auth.UserRegisterRequest;
import com.ignit.internship.model.auth.User;
import com.ignit.internship.repository.auth.UserRepository;

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

    public void register(UserRegisterRequest register) {
        User registeredUser = userRepository.save(new User(
            register.getUsername(),
            passwordEncoder.encode(register.getPassword()),
            register.getEmail(),
            new SimpleGrantedAuthority("ROLE_USER")
        ));

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(registeredUser.getEmail());
        mailMessage.setSubject("Ignit User Verification");
        mailMessage.setText("Verify by clicking this link below:\n" + baseUrl + "/api/auth/verify?token=" + registeredUser.getVerificationToken());

        emailService.sendEmail(mailMessage);
    }

    public void verify(String token) throws AuthenticationException {
        User user = userRepository.findByVerificationToken(token).orElseThrow(() -> new VerifyError("Verification failed"));
        user.setEnabled(true);
        user.setVerificationToken(null);

        userRepository.save(user);
    }

    public User authenticate(UserLoginRequest login) throws Exception {
        User user = userRepository.findByUsername(login.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Password not match");
        }

        if (!user.isEnabled()) {
            throw new Exception("User not verified");
        }

        return user;
    }

    public void updatePassword() {
        
    }
}
