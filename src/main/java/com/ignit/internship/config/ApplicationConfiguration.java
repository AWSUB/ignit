package com.ignit.internship.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.ForwardedHeaderFilter;

import com.ignit.internship.model.auth.User;
import com.ignit.internship.repository.auth.UserRepository;

@Configuration
public class ApplicationConfiguration {

    private final UserRepository userRepository;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.email}")
    private String adminEmail;

    public ApplicationConfiguration(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
 
    @Bean
    ForwardedHeaderFilter forwardedHeaderFilter() {
        return new ForwardedHeaderFilter();
    }
    
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    CommandLineRunner createDefaultAdmin() {
        return _ -> {
            if (userRepository.findByUsername(adminUsername).isEmpty()) {
                User admin = new User(
                    adminUsername, 
                    passwordEncoder().encode(adminPassword),
                    adminEmail,
                    new SimpleGrantedAuthority("ROLE_USER"),
                    new SimpleGrantedAuthority("ROLE_ADMIN")
                );
                admin.setEnabled(true);
                admin.setVerificationToken(null);
                userRepository.save(admin);
            }
        };
    }
}
