package com.ignit.internship.config;

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
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User(
                    "admin", 
                    passwordEncoder().encode("admin"),
                    "admin@admin",
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
